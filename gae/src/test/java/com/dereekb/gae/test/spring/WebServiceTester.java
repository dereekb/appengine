package com.dereekb.gae.test.spring;

import java.util.List;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public interface WebServiceTester {

	// MARK: Mock Requests
	public void performHttpRequests(List<MockHttpServletRequestBuilder> mockRequests) throws Exception;

	public ResultActions performHttpRequest(MockHttpServletRequestBuilder request) throws Exception;

	public ResultActions performHttpRequest(MockHttpServletRequestBuilder request,
	                                        String tokenOverride)
	        throws Exception;

	public ResultActions performSecureHttpRequest(MockHttpServletRequestBuilder request,
	                                              String token)
	        throws Exception;

	public ResultActions mockMvcPerform(RequestBuilder requestBuilder) throws Exception;

}
