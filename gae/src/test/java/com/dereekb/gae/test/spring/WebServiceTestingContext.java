package com.dereekb.gae.test.spring;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.List;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilter;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;

/**
 * {@link CoreServiceTestContext} extension that adds access to a
 * {@link WebApplicationContext}, and sets up Spring Security for logins.
 *
 * @author dereekb
 *
 */
public class WebServiceTestingContext extends CoreServiceTestingContext {

	public static final String WEB_TESTING_XML_PATH = BASE_TESTING_PATH + "testing-web.xml";

	@Autowired(required = false)
	protected TestLoginTokenContext testLoginTokenContext;

	@Autowired(required = false)
	protected FilterChainProxy springSecurityFilterChain;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUpWebServices() {
		DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);

		if (this.springSecurityFilterChain != null) {
			mockMvcBuilder = mockMvcBuilder.apply(springSecurity(this.springSecurityFilterChain));
		}

		this.mockMvc = mockMvcBuilder.build();
		TestLocalTaskQueueCallback.mockMvc = this.mockMvc;

		if (this.springSecurityFilterChain != null && this.testLoginTokenContext != null) {
			this.testLoginTokenContext.generateSystemAdmin();
			TestLocalTaskQueueCallback.waitUntilComplete();
		}
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
	protected void performHttpRequests(List<MockHttpServletRequestBuilder> mockRequests) throws Exception {
		for (MockHttpServletRequestBuilder request : mockRequests) {
			this.performHttpRequest(request);
		}
	}

	protected ResultActions performHttpRequest(MockHttpServletRequestBuilder request) throws Exception {
		String token = null;

		if (this.testLoginTokenContext != null) {
			token = this.testLoginTokenContext.getToken();
		}

		return this.performSecureHttpRequest(request, token);
	}

	protected ResultActions performSecureHttpRequest(MockHttpServletRequestBuilder request,
	                                                 String token)
	        throws Exception {
		if (token != null) {
			request.header(LoginTokenAuthenticationFilter.DEFAULT_HEADER_STRING,
			        LoginTokenAuthenticationFilter.buildTokenHeader(token));
		}

		return this.mockMvcPerform(request);
	}

	protected ResultActions mockMvcPerform(RequestBuilder requestBuilder) throws Exception {
		// Clear any context before running to prevent oddities.

		SecurityContextHolder.clearContext();
		ResultActions actions = this.mockMvc.perform(requestBuilder);
		this.resetAuthContext();	// Reset the auth context.

		return actions;
	}

}
