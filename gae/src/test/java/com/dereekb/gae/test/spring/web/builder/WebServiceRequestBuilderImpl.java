package com.dereekb.gae.test.spring.web.builder;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class WebServiceRequestBuilderImpl
        implements WebServiceRequestBuilder {

	public static final WebServiceRequestBuilder SINGLETON = new WebServiceRequestBuilderImpl();

	private boolean secure = true;

	// MARK: WebServiceRequestBuilder
	@Override
	public MockHttpServletRequestBuilder get(String urlTemplate,
	                                         Object... uriVars) {
		return this.request(HttpMethod.GET, urlTemplate, uriVars);
	}

	@Override
	public MockHttpServletRequestBuilder post(String urlTemplate,
	                                          Object... uriVars) {
		return this.request(HttpMethod.POST, urlTemplate, uriVars);
	}

	@Override
	public MockHttpServletRequestBuilder put(String urlTemplate,
	                                         Object... uriVars) {
		return this.request(HttpMethod.PUT, urlTemplate, uriVars);
	}

	@Override
	public MockHttpServletRequestBuilder delete(String urlTemplate,
	                                            Object... uriVars) {
		return this.request(HttpMethod.DELETE, urlTemplate, uriVars);
	}

	@Override
	public MockHttpServletRequestBuilder request(HttpMethod method,
	                                             String urlTemplate,
	                                             Object... uriVars) {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request(method, urlTemplate, uriVars);
		builder.secure(this.secure);
		return builder;
	}
}
