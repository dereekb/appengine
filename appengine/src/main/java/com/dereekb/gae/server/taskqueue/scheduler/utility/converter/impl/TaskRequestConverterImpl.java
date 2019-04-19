package com.dereekb.gae.server.taskqueue.scheduler.utility.converter.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.utility.TaskOptionsUtility;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestConverter;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestReader;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
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
public class TaskRequestConverterImpl extends AbstractDirectionalConverter<TaskRequest, TaskOptions>
        implements TaskRequestConverter {

	private static final SimplePath DEFAULT_RESOURCE = new SimplePathImpl("/taskqueue");

	/**
	 * The base system SimplePath/resource to submit the task to.
	 */
	private SimplePath resource = DEFAULT_RESOURCE;

	/**
	 * Default method type to submit request as.
	 * <p>
	 * Is used only if the input request does not have a type specified.
	 */
	private Method method;

	/**
	 * (Optional) Default timings to use if the request does not specify
	 * timings.
	 */
	private TaskRequestTiming timings;

	public TaskRequestConverterImpl() {
		this(DEFAULT_RESOURCE, null, null);
	}

	public TaskRequestConverterImpl(String url) {
		this(new SimplePathImpl(url), null, null);
	}

	public TaskRequestConverterImpl(SimplePath resource) {
		this(resource, null, null);
	}

	public TaskRequestConverterImpl(SimplePath resource, Method method, TaskRequestTiming timings) {
		this.setResource(resource);
		this.setMethod(method);
		this.setTimings(timings);
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

	@Override
	public TaskOptions convertSingle(TaskRequest input) throws ConversionFailureException {
		TaskRequestReaderImpl reader = this.makeReader(input);
		TaskOptionBuilder builder = new TaskOptionBuilder(reader);
		return builder.buildTaskOptions();
	}

	@Override
	public TaskRequestReaderImpl makeReader(TaskRequest request) {
		return new TaskRequestReaderImpl(request);
	}

	private class TaskRequestReaderImpl
	        implements TaskRequestReader {

		private final TaskRequest request;

		public TaskRequestReaderImpl(TaskRequest request) {
			this.request = request;
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

			if (TaskRequestConverterImpl.this.resource != null) {
				path = TaskRequestConverterImpl.this.resource.append(path);
			}

			return path.getPath();
		}

		@Override
		public Method getMethod() {
			Method method = this.request.getMethod();

			if (method == null) {
				method = TaskRequestConverterImpl.this.method;
			}

			return method;
		}

		@Override
		public TaskRequestTiming getTiming() {
			TaskRequestTiming timing = this.request.getTimings();

			if (timing == null) {
				timing = TaskRequestConverterImpl.this.timings;
			}

			return timing;
		}

		@Override
		public Collection<KeyedEncodedParameter> getHeaders() {
			return this.request.getHeaders();
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

	}

	private class TaskOptionBuilder {

		private final TaskRequestReaderImpl reader;

		public TaskOptionBuilder(TaskRequestReaderImpl reader) {
			this.reader = reader;
		}

		public TaskOptions buildTaskOptions() {
			TaskOptions options = this.newTaskOptions();
			options = this.updateTimings(options);
			options = this.appendHeaders(options);
			options = this.appendParameters(options);
			options = this.appendPayload(options);
			return options;
		}

		private TaskOptions newTaskOptions() {
			String url = this.reader.getFullRequestUri();
			String name = this.reader.getName();

			Method method = this.reader.getMethod();
			TaskOptions options = TaskOptions.Builder.withUrl(url).method(method).taskName(name);
			return options;
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

			return options;
		}

		private TaskOptions appendParameters(TaskOptions options) {
			Collection<KeyedEncodedParameter> parameters = this.reader.getParameters();

			if (parameters != null) {
				options = TaskOptionsUtility.appendParameters(options, parameters);
			}

			return options;
		}

		private TaskOptions appendPayload(TaskOptions options) {
			String payload = this.reader.getPayload();

			if (payload != null) {
				options = options.payload(payload);
			}

			return options;
		}
	}

}
