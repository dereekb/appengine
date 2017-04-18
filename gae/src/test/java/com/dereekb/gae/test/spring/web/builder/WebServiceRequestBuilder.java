package com.dereekb.gae.test.spring.web.builder;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Interface for generating web service requests. Wraps the default
 * {@link MockMvcRequestBuilders} functions in order to make sure requests are
 * for a specific servlet, etc.
 * 
 * @author dereekb
 *
 */
public interface WebServiceRequestBuilder {

	public MockHttpServletRequestBuilder get(String urlTemplate,
	                                         Object... uriVars);

	public MockHttpServletRequestBuilder post(String urlTemplate,
	                                          Object... uriVars);

	public MockHttpServletRequestBuilder put(String urlTemplate,
	                                         Object... uriVars);

	public MockHttpServletRequestBuilder delete(String urlTemplate,
	                                            Object... uriVars);

	public MockHttpServletRequestBuilder request(HttpMethod method,
	                                             String urlTemplate,
	                                             Object... uriVars);

}
