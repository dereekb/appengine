package com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.MediaType;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.SecuredTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestDataType;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.AppEngineTaskRequestConverter;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.TaskRequestHost;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.TaskRequestReader;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.exception.TaskRequestConversionException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.TaskOptionsUtility;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Used for converting {@link TaskRequest} to {@link TaskOption}.
 *
 * @author dereekb
 *
 */
public class AppEngineTaskRequestConverterImpl extends AbstractDirectionalConverter<SecuredTaskRequest, TaskOptions>
        implements AppEngineTaskRequestConverter {

	private static final SimplePath DEFAULT_RESOURCE = new SimplePathImpl("/taskqueue");
	private static final String HOST_HEADER = "Host";
	private static final String JSON_PARAMETER = "json";

	private boolean shouldAssertPathNotEmpty = true;

	/**
	 * The base system SimplePath/resource to submit the task to.
	 */
	private SimplePath resource = DEFAULT_RESOURCE;

	/**
	 * Default method type to submit request as.
	 * <p>
	 * Is used only if the input request does not have a type specified.
	 */
	private Method method = Method.PUT;

	/**
	 * (Optional) Default timings to use if the request does not specify
	 * timings.
	 */
	private TaskRequestTiming timings;

	/**
	 * (Optional) Host to send the task to.
	 */
	private TaskRequestHost host;

	public AppEngineTaskRequestConverterImpl() {
		this(DEFAULT_RESOURCE, null, null);
	}

	public AppEngineTaskRequestConverterImpl(String url) {
		this(new SimplePathImpl(url), null, null);
	}

	public AppEngineTaskRequestConverterImpl(SimplePath resource) {
		this(resource, null, null);
	}

	public AppEngineTaskRequestConverterImpl(SimplePath resource, Method method, TaskRequestTiming timings) {
		this(resource, method, timings, null);
	}

	public AppEngineTaskRequestConverterImpl(SimplePath resource,
	        Method method,
	        TaskRequestTiming timings,
	        TaskRequestHost host) {
		this.setResource(resource);
		this.setMethod(method);
		this.setTimings(timings);
		this.setHost(host);
	}

	public boolean shouldAssertPathNotEmpty() {
		return this.shouldAssertPathNotEmpty;
	}

	public void setShouldAssertPathNotEmpty(boolean shouldAssertPathNotEmpty) {
		this.shouldAssertPathNotEmpty = shouldAssertPathNotEmpty;
	}

	public SimplePath getResource() {
		return this.resource;
	}

	public void setResource(SimplePath resource) {
		this.resource = resource;
	}

	public Method getMethod() {
		return this.method;
	}

	public void setMethod(Method method) {
		if (method == null) {
			method = Method.PUT;
		}

		this.method = method;
	}

	public TaskRequestTiming getTimings() {
		return this.timings;
	}

	public void setTimings(TaskRequestTiming timings) {
		this.timings = timings;
	}

	public TaskRequestHost getHost() {
		return this.host;
	}

	public void setHostTarget(String hostTarget) {
		if (hostTarget != null) {
			this.setHost(new TaskRequestHostImpl(hostTarget));
		} else {
			this.host = null;
		}
	}

	public void setHost(TaskRequestHost host) {
		this.host = host;
	}

	@Override
	public TaskOptions convertSingle(SecuredTaskRequest input) throws ConversionFailureException {
		TaskRequestReaderImpl reader = this.makeReader(input);
		TaskOptionBuilder builder = new TaskOptionBuilder(reader);
		return builder.buildTaskOptions();
	}

	@Override
	public TaskRequestReaderImpl makeReader(SecuredTaskRequest request) {
		return new TaskRequestReaderImpl(request);
	}

	private final class TaskRequestReaderImpl
	        implements TaskRequestReader {

		private final TaskRequest request;
		private final SecuredTaskRequest securedRequest;

		public TaskRequestReaderImpl(SecuredTaskRequest securedRequest) {
			this.securedRequest = securedRequest;
			this.request = securedRequest.getTaskRequest();
		}

		@Override
		public TaskRequest getTaskRequest() {
			return this.request;
		}

		@Override
		public String getName() {
			return this.request.getName();
		}

		@Override
		public String getFullRequestUri() {
			SimplePath path = this.request.getPath();

			if (AppEngineTaskRequestConverterImpl.this.resource != null) {
				path = AppEngineTaskRequestConverterImpl.this.resource.append(path);
			}

			return path.getPath();
		}

		@Override
		public TaskRequestTiming getTiming() {
			TaskRequestTiming timing = this.request.getTimings();

			if (timing == null) {
				timing = AppEngineTaskRequestConverterImpl.this.timings;
			}

			return timing;
		}

		@Override
		public Collection<KeyedEncodedParameter> getHeaders() {
			List<KeyedEncodedParameter> allHeaders = new ArrayList<KeyedEncodedParameter>();
			Collection<KeyedEncodedParameter> headers = this.request.getHeaders();
			Collection<KeyedEncodedParameter> securityHeaders = this.securedRequest.getSecurityHeaders();

			ListUtility.addElements(allHeaders, headers);
			ListUtility.addElements(allHeaders, securityHeaders);

			return allHeaders;
		}

		public TaskRequestDataType getDataType() {
			return this.request.getDataType();
		}

		@Override
		public Collection<KeyedEncodedParameter> getParameters() {
			switch (this.request.getDataType()) {
				case PARAMETERS:
					return this.request.getParameters();
				case JSON:
				default:
					return null;
			}
		}

		public String getRequestData()
		{
			return this.request.getRequestData();
		}

		@Deprecated
		@Override
		public String getPayload() {
			switch (this.request.getDataType()) {
				case JSON:
					return this.request.getRequestData();
				case PARAMETERS:
				default:
					return null;
			}
		}

		public TaskRequestHost getHost() {
			TaskRequestHost host = this.request.getHost();

			if (host == null) {
				host = AppEngineTaskRequestConverterImpl.this.host;
			}

			return host;
		}

	}

	private class TaskOptionBuilder {

		private final TaskRequestReaderImpl reader;

		public TaskOptionBuilder(TaskRequestReaderImpl reader) {
			this.reader = reader;
		}

		public TaskOptions buildTaskOptions() throws TaskRequestConversionException {
			TaskOptions options = this.newTaskOptions();
			options = this.updateTimings(options);
			options = this.appendHeaders(options);
			options = this.appendParameters(options);
			options = this.appendPayload(options);
			return options;
		}

		private TaskOptions newTaskOptions() {
			String url = this.buildUrl();
			String name = this.reader.getName();

			Method method = AppEngineTaskRequestConverterImpl.this.method;
			TaskOptions options = TaskOptions.Builder.withUrl(url).method(method).taskName(name);
			return options;
		}

		private String buildUrl() {
			if (AppEngineTaskRequestConverterImpl.this.shouldAssertPathNotEmpty) {
				SimplePath taskPath = this.reader.request.getPath();

				if (taskPath.getPathComponents().isEmpty()) {
					throw new TaskRequestConversionException("Path's string can not be empty.");
				}
			}

			return this.reader.getFullRequestUri();
		}

		private TaskOptions updateTimings(TaskOptions options) {
			TaskRequestTiming timing = this.reader.getTiming();

			if (timing != null) {
				Long time = timing.getTime();

				switch (timing.getTimingType()) {
					case ETA:
						options = options.etaMillis(time);
						break;
					case COUNTDOWN:
						options = options.countdownMillis(time);
						break;
				}
			}

			return options;
		}

		private TaskOptions appendHeaders(TaskOptions options) {
			Collection<KeyedEncodedParameter> headers = this.reader.getHeaders();

			if (headers != null) {
				options = TaskOptionsUtility.appendHeaders(options, headers);
			}

			// Add the host header if there is a custom host.
			TaskRequestHost host = this.reader.getHost();

			if (host != null) {
				String target = host.getHostTarget();
				KeyedEncodedParameter hostParameter = new KeyedEncodedParameterImpl(HOST_HEADER, target);
				options = TaskOptionsUtility.appendHeader(options, hostParameter);
			}

			// If there is no host it will be executed on the same application
			// that submitted it.

			return options;
		}

		private TaskOptions appendParameters(TaskOptions options) {
			Collection<KeyedEncodedParameter> parameters = this.reader.getParameters();

			if (parameters != null) {
				options = TaskOptionsUtility.appendParameters(options, parameters);
			}

			return options;
		}

		private TaskOptions appendBody(TaskOptions options) {

			// Payload changed.

			String requestData = this.reader.getRequestData();

			if (requestData != null) {

				switch (this.reader.getDataType()) {
					case JSON:
						options = options.param(JSON_PARAMETER, requestData);
						break;
					case PARAMETERS:
					default:
						// Do nothing...
						break;
				}

			}

			return options;
		}

		private TaskOptions appendPayload(TaskOptions options) {
			String payload = this.reader.getPayload();

			if (payload != null) {

				switch (this.reader.getDataType()) {
					case JSON:
						// Set Payload
						byte[] jsonBytes;

						try {
							jsonBytes = payload.getBytes("UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}

						// Set
						options = options.payload(jsonBytes, MediaType.APPLICATION_JSON_VALUE);
						break;
					case PARAMETERS:
					default:
						// Set Payload Normally
						break;
				}

			}

			return options;
		}
	}

}
