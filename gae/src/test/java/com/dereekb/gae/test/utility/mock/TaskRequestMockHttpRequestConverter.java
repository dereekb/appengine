package com.dereekb.gae.test.utility.mock;

import java.util.Collection;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskParameter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestConverter;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestReader;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Used for mocking taskqueue requests.
 * 
 * @author dereekb
 *
 */
public class TaskRequestMockHttpRequestConverter extends AbstractDirectionalConverter<TaskRequest, MockHttpServletRequestBuilder> {

	private TaskRequestConverter converter;

	public TaskRequestMockHttpRequestConverter(TaskRequestConverter converter) throws IllegalArgumentException {
		this.setConverter(converter);
	}

	public TaskRequestConverter getConverter() {
		return this.converter;
	}

	public void setConverter(TaskRequestConverter converter) throws IllegalArgumentException {
		if (converter == null) {
			throw new IllegalArgumentException();
		}

		this.converter = converter;
	}

	@Override
	public MockHttpServletRequestBuilder convertSingle(TaskRequest input) throws ConversionFailureException {
		TaskRequestReader reader = this.converter.makeReader(input);
		MockHttpServletRequestBuilder builder = makeBuilder(reader);

		appendHeaders(builder, input.getHeaders());
		appendParameters(builder, input.getParameters());

		return builder;
	}

	public static MockHttpServletRequestBuilder makeBuilder(TaskRequestReader reader) {
		Method method = reader.getMethod();
		String url = reader.getFullRequestUri();
		return makeBuilder(method, url);
	}

	public static MockHttpServletRequestBuilder makeBuilder(Method method,
	                                                        String url) {
		MockHttpServletRequestBuilder builder = null;

		switch (method) {
			case DELETE:
				builder = MockMvcRequestBuilders.delete(url);
				break;
			case GET:
				builder = MockMvcRequestBuilders.get(url);
				break;
			case POST:
				builder = MockMvcRequestBuilders.post(url);
				break;
			case PUT:
				builder = MockMvcRequestBuilders.put(url);
				break;
			default:
				break;
		}

		return builder;
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
	                                 Collection<TaskParameter> parameters) {
		if (parameters != null) {
			for (TaskParameter parameter : parameters) {
				builder.header(parameter.getParameter(), parameter.getValue());
			}
		}
	}

	public static void appendParameters(MockHttpServletRequestBuilder builder,
	                                    Collection<TaskParameter> parameters) {
		if (parameters != null) {
			for (TaskParameter parameter : parameters) {
				builder.param(parameter.getParameter(), parameter.getValue());
			}
		}
	}

}
