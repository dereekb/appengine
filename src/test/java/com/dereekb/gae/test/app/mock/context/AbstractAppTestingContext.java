package com.dereekb.gae.test.app.mock.context;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.EncodedLoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterService;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.test.app.mock.client.crud.MockClientRequestSender;
import com.dereekb.gae.test.app.mock.web.WebServiceTester;
import com.dereekb.gae.test.app.mock.web.builder.ServletAwareWebServiceRequestBuilder;
import com.dereekb.gae.test.app.mock.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.server.auth.impl.TestAuthenticationContext;
import com.dereekb.gae.test.utility.mock.MockHttpServletRequestBuilderUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.web.api.server.initialize.ApiInitializeServerController;
import com.google.appengine.api.taskqueue.dev.LocalTaskQueueCallback;
import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

/**
 * {@link AbstractAppContextOnlyTestingContext} extension that loads the application's context, API and Taskqueue.
 *
 * @author dereekb
 *
 */
@DirtiesContext
@WebAppConfiguration
@SpringJUnitConfig( name = "api", locations = {
		AbstractAppTestingContext.API_APPLICATION_XML_PATH,
		AbstractAppTestingContext.TASKQUEUE_APPLICATION_XML_PATH,
		AbstractAppTestingContext.WEB_TESTING_XML_PATH })
public class AbstractAppTestingContext extends AbstractAppContextOnlyTestingContext implements WebServiceTester {

	public static final String API_APPLICATION_XML_PATH = AbstractAppTestingContext.BASE_MAIN_PATH + "spring/api/api.xml";
	public static final String TASKQUEUE_APPLICATION_XML_PATH = AbstractAppTestingContext.BASE_MAIN_PATH + "spring/taskqueue/taskqueue.xml";

	public static final String WEB_TESTING_XML_PATH = BASE_TESTING_PATH + "testing-web.xml";

	@Autowired(required = false)
	@Qualifier("testSecurityServletPath")
	protected String defaultSecurityServletPath = null;

	@Autowired(required = false)
	@Qualifier("testServiceRequestBuilder")
	protected WebServiceRequestBuilder serviceRequestBuilder = new ServletAwareWebServiceRequestBuilder();

	@Autowired(required = false)
	protected TestLoginTokenContext testLoginTokenContext;

	@Autowired(required = false)
	protected AuthenticationParameterService authParameterService = AuthenticationParameterServiceImpl.SINGLETON;

	@Autowired(required = false)
	protected FilterChainProxy springSecurityFilterChain;

	@Autowired
	protected LocalTaskQueueTestConfig taskQueueTestConfig;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	protected ApiInitializeServerController initializeServerController;

	@Autowired(required = false)
	protected TestAuthenticationContext authContext;

	@Autowired
	@Qualifier("mockClientRequestSender")
	private MockClientRequestSender mockRequestSender;

	private MockMvc mockMvc;

	public AbstractAppTestingContext() {}

	public WebApplicationContext getWebApplicationContext() {
		return this.webApplicationContext;
	}

	public MockMvc getMockMvc() {
		return this.mockMvc;
	}

	// MARK: Setup
	@Override
	@BeforeEach
	public void setUpAppServices() {
		super.setUpAppServices();
		this.setUpWebServices();

		this.initializeMockRequestSender();
		this.initializeServerAndAuthContext();
	}

	@Override
	@AfterEach
	public void tearDownAppServices() {
		super.tearDownAppServices();

		// Wait for any tasks to complete first...
		waitUntilTaskQueueCompletes();
	}

	// MARK: Initialize
	public void setUpWebServices() {
		DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);

		if (this.springSecurityFilterChain != null) {
			// Make sure all requests come from the servlet path specified:

			if (this.defaultSecurityServletPath != null) {
				mockMvcBuilder = mockMvcBuilder.defaultRequest(
				        this.serviceRequestBuilder.get("/").servletPath(this.defaultSecurityServletPath));
			}

			mockMvcBuilder = mockMvcBuilder.apply(springSecurity(this.springSecurityFilterChain));
		}

		this.mockMvc = mockMvcBuilder.build();
		TaskQueueCallbackHandler.serviceRequestBuilder = this.serviceRequestBuilder;
		TaskQueueCallbackHandler.mockMvc = this.mockMvc;
	}

	protected void initializeMockRequestSender() {
		this.mockRequestSender.setWebServiceTester(this);
	}

	protected void initializeServerAndAuthContext() {
		this.initializeServer();
		this.resetAuthContext();

		// Initialize the system admin for the security chain
		if (this.springSecurityFilterChain != null && this.testLoginTokenContext != null) {
			this.testLoginTokenContext.generateSystemAdmin();
			TaskQueueCallbackHandler.waitUntilComplete();
		}
	}

	protected final void resetAuthContext() {
		if (this.authContext != null) {
			this.authContext.resetContext();
		}
	}

	protected void initializeServer() {
		if (this.initializeServerController != null) {
			this.initializeServerController.initialize();
		}
	}

	// MARK: Utility
	public ClientRequestSecurity getRequestSecurity() {
		EncodedLoginToken overrideToken = new EncodedLoginTokenImpl(this.testLoginTokenContext.getToken());
		return new ClientRequestSecurityImpl(overrideToken);
	}

	// MARK: Mock Requests
	@Override
	public WebServiceRequestBuilder getRequestBuilder() {
		return this.serviceRequestBuilder;
	}

	@Override
	public void performHttpRequests(List<MockHttpServletRequestBuilder> mockRequests) throws Exception {
		for (MockHttpServletRequestBuilder request : mockRequests) {
			this.performHttpRequest(request);
		}
	}

	@Override
	public ResultActions performHttpRequest(MockHttpServletRequestBuilder request) throws Exception {
		String token = null;

		if (this.testLoginTokenContext != null) {
			token = this.testLoginTokenContext.getToken();
		}

		return this.performSecureHttpRequest(request, token);
	}

	@Override
	public ResultActions performHttpRequest(MockHttpServletRequestBuilder request,
	                                        String tokenOverride)
	        throws Exception {

		if (tokenOverride == null && this.testLoginTokenContext != null) {
			tokenOverride = this.testLoginTokenContext.getToken();
		}

		return this.performSecureHttpRequest(request, tokenOverride);
	}

	@Override
	public ResultActions performSecureHttpRequest(MockHttpServletRequestBuilder request,
	                                              String token)
	        throws Exception {
		if (token != null) {
			KeyedEncodedParameter authParameter = this.authParameterService.buildTokenAuthenticationParameter(token);
			request.header(authParameter.keyValue(), authParameter.getParameterString());
		}

		return this.mockMvcPerform(request);
	}

	@Override
	public ResultActions mockMvcPerform(RequestBuilder requestBuilder) throws Exception {
		// Clear any context before running to prevent oddities.

		SecurityContextHolder.clearContext();
		ResultActions actions = this.mockMvc.perform(requestBuilder);
		this.resetAuthContext();	// Reset the auth context.

		return actions;
	}

	// MARK: Taskqueue
	@Override
	public void waitForTaskQueueToComplete() {
		waitUntilTaskQueueCompletes();
	}

	public static void waitUntilTaskQueueCompletes() {
		TaskQueueCallbackHandler.waitUntilComplete();
	}

	/**
	 * Internal component that handles all Taskqueue callbacks.
	 *
	 * @author dereekb
	 *
	 */
	public static class TaskQueueCallbackHandler
	        implements LocalTaskQueueCallback {

		private static final long serialVersionUID = 1L;

		private static boolean LOG_EVENTS = true;
		private static boolean FINE_LOG_EVENTS = false;

		public static MockMvc mockMvc;

		public static WebServiceRequestBuilder serviceRequestBuilder;

		public static final AtomicLong ATOMIC_COUNTER = new AtomicLong(0L);

		private static final int MAX_WAITS = 20;
		private static final int WAIT_TIME = 200;

		public static final void safeWaitForLatch() throws InterruptedException {
			int waits = 0;

			if (FINE_LOG_EVENTS) {
				System.out.println("Waiting for latch....");
			}

			waitForLatch(0);

			while (ATOMIC_COUNTER.get() != 0) {
				waits += 1;

				if (FINE_LOG_EVENTS) {
					System.out.println("Still waiting...");
				}

				waitForLatch(waits);

				if (waits >= MAX_WAITS) {
					System.out.println("Reached maximum wait limit.");
					break;
				}
			}
		}

		private static final void waitForLatch(int waits) throws InterruptedException {
			Thread.sleep(WAIT_TIME + (waits * WAIT_TIME));
		}

		private static final void increaseLatchCounter() {
			Long count = ATOMIC_COUNTER.incrementAndGet();

			if (FINE_LOG_EVENTS) {
				System.out.println(String.format("Latch increasing to %s.", count));
			}
		}

		private static final void decreaseLatchCounter() {
			Long count = ATOMIC_COUNTER.decrementAndGet();

			if (FINE_LOG_EVENTS) {
				System.out.println(String.format("Latch decreased to %s.", count));
			}
		}

		private static final int TASKQUEUE_TASK_WAIT_TIME = 10;

		@Override
		public int execute(URLFetchRequest arg0) {
			increaseLatchCounter();

			if (LOG_EVENTS) {
				System.out.println(String.format("Executing TQ task %s -> %s", arg0.getMethod(), arg0.getUrl()));
			}

			MockHttpServletRequestBuilder requestBuilder;

			try {
				requestBuilder = this.buildRequest(arg0);
			} catch (UnsupportedEncodingException e) {
				System.out.println(String.format("Exception decoding URL: %s.", arg0.getUrl()));
				e.printStackTrace();
				decreaseLatchCounter();
				return 0;
			}

			try {
				Thread.sleep(ATOMIC_COUNTER.get() * TASKQUEUE_TASK_WAIT_TIME);
			} catch (InterruptedException e1) {

			}

			// Start Objectify service for the thread.
			Closeable ofy = ObjectifyService.begin();

			try {
				ResultActions resultActions = mockMvc.perform(requestBuilder);
				MockHttpServletResponse response = resultActions.andReturn().getResponse();
				int status = response.getStatus();
				// String error = response.getErrorMessage();
				return status;
			} catch (ServletException e) {
				e.printStackTrace();
				return 500;
			} catch (Exception e) {
				System.out.println(String.format("Exception occured while executing TQ task %s.", arg0.getUrl()));
				e.printStackTrace();
				return 0;
			} finally {
				if (LOG_EVENTS) {
					System.out.println(String.format("Finished TQ task at %s.", arg0.getUrl()));
				}

				ofy.close();
				decreaseLatchCounter();
			}
		}

		private MockHttpServletRequestBuilder buildRequest(URLFetchRequest arg0) throws UnsupportedEncodingException {
			return MockHttpServletRequestBuilderUtility.convert(arg0, serviceRequestBuilder);
		}

		@Override
		public void initialize(Map<String, String> arg0) {}

		public static void waitUntilComplete() {
			try {
				safeWaitForLatch();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (FINE_LOG_EVENTS) {
				System.out.println("Stopped waiting for TaskQueue operation.");
			}
		}

	}

}
