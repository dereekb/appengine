package com.dereekb.gae.test.spring;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterService;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.spring.web.builder.ServletAwareWebServiceRequestBuilder;
import com.dereekb.gae.test.spring.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.test.utility.mock.MockHttpServletRequestBuilderUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.google.appengine.api.taskqueue.dev.LocalTaskQueueCallback;
import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

/**
 * {@link CoreServiceTestContext} extension that adds access to a
 * {@link WebApplicationContext}, and sets up Spring Security for logins.
 *
 * @author dereekb
 *
 */
public class WebServiceTestingContextImpl extends CoreServiceTestingContext
        implements WebServiceTester {

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
	protected FilterChainProxy springSecurityFilterChain;

	@Autowired(required = false)
	protected AuthenticationParameterService authParameterService = AuthenticationParameterServiceImpl.SINGLETON;

	@Autowired
	protected LocalTaskQueueTestConfig taskQueueTestConfig;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
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
		TestLocalTaskQueueCallback.serviceRequestBuilder = this.serviceRequestBuilder;
		TestLocalTaskQueueCallback.mockMvc = this.mockMvc;

		if (this.springSecurityFilterChain != null && this.testLoginTokenContext != null) {
			this.testLoginTokenContext.generateSystemAdmin();
			TestLocalTaskQueueCallback.waitUntilComplete();
		}
	}

	@Override
	@Before
	public void setUpCoreServices() {
		// this.taskQueueTestConfig.setTaskExecutionLatch(TestLocalTaskQueueCallback.countDownLatch);
		super.setUpCoreServices();
	}
	
	@Override
	@After
	public void tearDownCoreServices() {
		super.tearDownCoreServices();
	
		// Wait for any tasks to complete first...
		waitUntilTaskQueueCompletes();
	}

	public WebApplicationContext getWebApplicationContext() {
		return this.webApplicationContext;
	}

	public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}

	public MockMvc getMockMvc() {
		return this.mockMvc;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
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
			KeyedEncodedParameter authParameter = this.authParameterService.buildAuthenticationParameter(token);
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

	@Override
	public void waitForTaskQueueToComplete() {
		waitUntilTaskQueueCompletes();
	}

	public static void waitUntilTaskQueueCompletes() {
		TestLocalTaskQueueCallback.waitUntilComplete();
	}

	public static class TestLocalTaskQueueCallback
	        implements LocalTaskQueueCallback {

		private static final long serialVersionUID = 1L;

		private static boolean LOG_EVENTS = true;

		public static MockMvc mockMvc;

		public static WebServiceRequestBuilder serviceRequestBuilder;

		public static final AtomicLong ATOMIC_COUNTER = new AtomicLong(0L);

		private static final int MAX_WAITS = 20;
		private static final int WAIT_TIME = 200;

		public static final void safeWaitForLatch() throws InterruptedException {
			int waits = 0;

			System.out.println("Waiting for latch....");

			waitForLatch(0);

			while (ATOMIC_COUNTER.get() != 0) {
				waits += 1;
				System.out.println("Still waiting...");
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
			System.out.println(String.format("Latch increasing to %s.", count));
		}

		private static final void decreaseLatchCounter() {
			Long count = ATOMIC_COUNTER.decrementAndGet();
			System.out.println(String.format("Latch decreased to %s.", count));
		}

		private static final int TASKQUEUE_TASK_WAIT_TIME = 10;

		@Override
		public int execute(URLFetchRequest arg0) {
			increaseLatchCounter();

			if (LOG_EVENTS) {
				System.out.println(String.format("Executing taskqueue task %s -> %s", arg0.getMethod(), arg0.getUrl()));
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
				System.out.println(String.format("Exception occured while executing task %s.", arg0.getUrl()));
				e.printStackTrace();
				return 0;
			} finally {
				if (LOG_EVENTS) {
					System.out.println(String.format("Finished task at %s.", arg0.getUrl()));
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

			System.out.println("Stopped waiting for TaskQueue operation.");
		}

	}

}
