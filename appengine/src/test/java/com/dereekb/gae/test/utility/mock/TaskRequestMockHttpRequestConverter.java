package com.dereekb.gae.test.utility.mock;

import java.util.Collection;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.SecuredTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.AppEngineTaskRequestConverter;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.TaskRequestReader;
import com.dereekb.gae.test.app.mock.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.test.app.mock.web.builder.impl.WebServiceRequestBuilderImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Used for mocking taskqueue requests.
 *
 * @author dereekb
 *
 * @deprecated Is no longer used anywhere.
 */
@Deprecated
public class TaskRequestMockHttpRequestConverter extends AbstractDirectionalConverter<SecuredTaskRequest, MockHttpServletRequestBuilder> {

	private AppEngineTaskRequestConverter converter;
	private WebServiceRequestBuilder requestBuilder;

	public TaskRequestMockHttpRequestConverter(AppEngineTaskRequestConverter converter, WebServiceRequestBuilder requestBuilder)
	        throws IllegalArgumentException {
		this.setConverter(converter);
		this.setRequestBuilder(requestBuilder);
	}

	public AppEngineTaskRequestConverter getConverter() {
		return this.converter;
	}

	public void setConverter(AppEngineTaskRequestConverter converter) throws IllegalArgumentException {
		if (converter == null) {
			throw new IllegalArgumentException();
		}

		this.converter = converter;
	}

	public WebServiceRequestBuilder getRequestBuilder() {
		return this.requestBuilder;
	}

	public void setRequestBuilder(WebServiceRequestBuilder requestBuilder) {
		if (requestBuilder == null) {
			throw new IllegalArgumentException("requestBuilder cannot be null.");
		}

		this.requestBuilder = requestBuilder;
	}

	// MARK: Converter
	@Override
	public MockHttpServletRequestBuilder convertSingle(SecuredTaskRequest input) throws ConversionFailureException {
		TaskRequestReader reader = this.converter.makeReader(input);
		MockHttpServletRequestBuilder builder = makeBuilder(reader, this.requestBuilder);

		appendHeaders(builder, reader.getHeaders());
		appendParameters(builder, reader.getParameters());

		return builder;
	}

	@Deprecated
	public static MockHttpServletRequestBuilder makeBuilder(TaskRequestReader reader) {
		return makeBuilder(reader, WebServiceRequestBuilderImpl.SINGLETON);
	}

	public static MockHttpServletRequestBuilder makeBuilder(TaskRequestReader reader,
	                                                        WebServiceRequestBuilder builder) {
		HttpMethod httpMethod = HttpMethod.PUT;	// Always PUT
		String url = reader.getFullRequestUri();
		return builder.request(httpMethod, url);
	}

	public static HttpMethod convertMethod(Method method) {
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

	public static void appendHeaders(MockHttpServletRequestBuilder builder,
	                                 Collection<KeyedEncodedParameter> parameters) {
		if (parameters != null) {
			for (KeyedEncodedParameter parameter : parameters) {
				builder.header(parameter.getParameterKey(), parameter.getParameterString());
			}
		}
	}

	public static void appendParameters(MockHttpServletRequestBuilder builder,
	                                    Collection<KeyedEncodedParameter> parameters) {
		if (parameters != null) {
			for (KeyedEncodedParameter parameter : parameters) {
				builder.param(parameter.getParameterKey(), parameter.getParameterString());
			}
		}
	}

}
