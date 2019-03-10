package com.dereekb.gae.test.spring;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext.TaskQueueCallbackHandler;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.spring.web.builder.ServletAwareWebServiceRequestBuilder;
import com.dereekb.gae.test.spring.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;

/**
 * {@link CoreServiceTestContext} extension that adds access to a
 * {@link WebApplicationContext}, and sets up Spring Security for logins.
 *
 * @author dereekb
 *
 * @deprecated Deprecated with move to JUnit5. Use {@link AbstractAppTestingContext} instead.
 */
@Deprecated
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

	public WebServiceTestingContextImpl() {
		this.initializeServerWithCoreServices = false;
	}

	@Before
	public void setupServices() {
		this.setUpWebServices();
		this.initializeServerAndAuthContext();
	}

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

	/*
	@Override
	@Before
	public void setUpCoreServices() {

		// Wait for the web services to be set up.=
		// this.taskQueueTestConfig.setTaskExecutionLatch(TaskQueueCallbackHandler.countDownLatch);
		super.setUpCoreServices();
	}
	*/

	@Override
	public void initializeServerAndAuthContext() {
		super.initializeServerAndAuthContext();

		// Initialize the system admin for the security chain
		if (this.springSecurityFilterChain != null && this.testLoginTokenContext != null) {
			this.testLoginTokenContext.generateSystemAdmin();
			TaskQueueCallbackHandler.waitUntilComplete();
		}
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

	@Override
	public void waitForTaskQueueToComplete() {
		waitUntilTaskQueueCompletes();
	}

	public static void waitUntilTaskQueueCompletes() {
		TaskQueueCallbackHandler.waitUntilComplete();
	}

}
