package com.dereekb.gae.test.spring;

import java.util.List;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.test.spring.web.builder.WebServiceRequestBuilder;

/**
 * Interface for performing web tests.
 *
 * @author dereekb
 *
 */
public interface WebServiceTester {

	public WebServiceRequestBuilder getRequestBuilder();

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

	/**
	 * Waits for any/all taskqueue requests to complete.
	 */
	public void waitForTaskQueueToComplete();

}
