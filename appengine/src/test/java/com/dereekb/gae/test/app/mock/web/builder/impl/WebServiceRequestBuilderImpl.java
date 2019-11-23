package com.dereekb.gae.test.app.mock.web.builder.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.test.app.mock.web.builder.WebServiceRequestBuilder;

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
	                                             URI uri) {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request(method, uri);
		builder.secure(this.secure);
		return builder;
	}

	@Override
	public MockHttpServletRequestBuilder request(HttpMethod method,
	                                             String urlTemplate,
	                                             Object... uriVars) {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request(method, urlTemplate, uriVars);
		builder.secure(this.secure);
		return builder;
	}

	@Override
	public MockMultipartHttpServletRequestBuilder multipartRequest(URI uri) {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(uri);
		builder.secure(this.secure);
		return builder;
	}

	@Override
	public MockMultipartHttpServletRequestBuilder multipartRequest(String urlTemplate,
	                                                               Object... uriVars) {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(urlTemplate, uriVars);
		builder.secure(this.secure);
		return builder;
	}

}
