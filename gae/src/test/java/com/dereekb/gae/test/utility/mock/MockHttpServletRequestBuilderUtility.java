package com.dereekb.gae.test.utility.mock;

import java.util.Map;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.utilities.misc.parameters.Parameters;

public class MockHttpServletRequestBuilderUtility {

	public static void addParameters(MockHttpServletRequestBuilder builder,
	                                 Parameters parameters) {

		Map<String, String> entryParameters = parameters.getParameters();
		addParameters(builder, entryParameters);
	}

	public static void addParameters(MockHttpServletRequestBuilder builder,
	                                 Map<String, String> parameters) {

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			builder.param(entry.getKey(), entry.getValue());
		}

	}

}
