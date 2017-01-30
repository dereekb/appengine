package com.dereekb.gae.test.utility.mock;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest;
import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest.Header;
import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest.RequestMethod;

public class MockHttpServletRequestBuilderUtility {

	private static String URL_PREFIX = "http://localhost:8080";

	public static void addParameters(MockHttpServletRequestBuilder builder,
	                                 Parameters parameters) {

		Map<String, String> entryParameters = parameters.getParameters();
		addParameters(builder, entryParameters);
	}

	public static void addHeaders(MockHttpServletRequestBuilder builder,
	                              Parameters parameters) {

		Map<String, String> entryParameters = parameters.getParameters();
		addHeaders(builder, entryParameters);
	}

	public static void addParameters(MockHttpServletRequestBuilder builder,
	                                 Map<String, String> parameters) {

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			builder.param(entry.getKey(), entry.getValue());
		}

	}

	public static void addHeaders(MockHttpServletRequestBuilder builder,
	                              Map<String, String> headers) {

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

	}

	// MARK: Google App Engine Task Queue
	public static MockHttpServletRequestBuilder convert(URLFetchRequest arg0) throws UnsupportedEncodingException {
		HttpMethod method = convert(arg0.getMethod());

		// Decode to prevent the mock request from encoding an encoded
		// value.
		String url = URLDecoder.decode(arg0.getUrl(), "UTF-8");
		url = url.replaceFirst(URL_PREFIX, "");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(method, url);

		Parameters headers = getHeaderParameters(arg0);
		addHeaders(requestBuilder, headers);

		return requestBuilder;
	}

	public static HttpMethod convert(RequestMethod method) {
		HttpMethod httpMethod = null;

		switch (method) {
			case DELETE:
				httpMethod = HttpMethod.DELETE;
				break;
			case GET:
				httpMethod = HttpMethod.GET;
				break;
			case HEAD:
				httpMethod = HttpMethod.HEAD;
				break;
			case PATCH:
				httpMethod = HttpMethod.PATCH;
				break;
			case POST:
				httpMethod = HttpMethod.POST;
				break;
			case PUT:
				httpMethod = HttpMethod.PUT;
				break;
			default:
				break;
		}

		return httpMethod;
	}

	public static Parameters getHeaderParameters(URLFetchRequest arg0) {
		List<Header> headers = arg0.getHeaderList();
		Map<String, String> map = new HashMap<String, String>();

		for (Header header : headers) {
			String key = header.getKey();
			String value = header.getValue();

			map.put(key, value);
		}

		return new ParametersImpl(map);
	}

}
