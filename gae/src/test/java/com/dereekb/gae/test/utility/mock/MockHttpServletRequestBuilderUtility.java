package com.dereekb.gae.test.utility.mock;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.test.spring.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.test.spring.web.builder.WebServiceRequestBuilderImpl;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.utilities.misc.path.SimplePath;
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

	// MARK: ClientRequest
	@Deprecated
	public static MockHttpServletRequestBuilder convert(ClientRequest request) {
		return convert(request, WebServiceRequestBuilderImpl.SINGLETON);
	}

	public static MockHttpServletRequestBuilder convert(ClientRequest request,
	                                                    WebServiceRequestBuilder serviceRequestBuilder) {
		HttpMethod method = convert(request.getMethod());

		SimplePath relativePath = request.getUrl().getRelativeUrlPath();
		String url = relativePath.getPath();

		MockHttpServletRequestBuilder requestBuilder = serviceRequestBuilder.request(method, url);

		Parameters headers = request.getHeaders();
		if (headers != null) {
			addHeaders(requestBuilder, headers);
		}

		Parameters parameters = request.getParameters();
		if (parameters != null) {
			addParameters(requestBuilder, parameters);
		}

		ClientRequestData data = request.getData();
		if (data != null) {
			String content = data.getDataString();
			requestBuilder.content(content);
			requestBuilder.contentType(MediaType.APPLICATION_JSON);
		}

		return requestBuilder;
	}

	// MARK: Google App Engine Task Queue
	@Deprecated
	public static MockHttpServletRequestBuilder convert(URLFetchRequest arg0) throws UnsupportedEncodingException {
		return convert(arg0, WebServiceRequestBuilderImpl.SINGLETON);
	}

	public static MockHttpServletRequestBuilder convert(URLFetchRequest arg0,
	                                                    WebServiceRequestBuilder serviceRequestBuilder)
	        throws UnsupportedEncodingException {
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

	public static HttpMethod convert(RequestMethod method) {
		return convertHttpMethod(method.name());
	}

	public static HttpMethod convert(ClientRequestMethod method) {
		return convertHttpMethod(method.name());
	}

	public static HttpMethod convertHttpMethod(String method) {
		HttpMethod httpMethod = null;

		switch (method) {
			case "DELETE":
				httpMethod = HttpMethod.DELETE;
				break;
			case "GET":
				httpMethod = HttpMethod.GET;
				break;
			case "HEAD":
				httpMethod = HttpMethod.HEAD;
				break;
			case "PATCH":
				httpMethod = HttpMethod.PATCH;
				break;
			case "POST":
				httpMethod = HttpMethod.POST;
				break;
			case "PUT":
				httpMethod = HttpMethod.PUT;
				break;
			default:
				break;
		}

		return httpMethod;
	}

}
