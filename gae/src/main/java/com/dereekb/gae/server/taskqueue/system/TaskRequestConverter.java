package com.dereekb.gae.server.taskqueue.system;

import java.util.Collection;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.common.base.Joiner;

/**
 * Used for converting {@link TaskRequest} to {@link TaskOption}.
 *
 * @author dereekb
 *
 */
public class TaskRequestConverter extends AbstractDirectionalConverter<TaskRequest, TaskOptions> {

	/**
	 * The base system URL/resource to submit the task to.
	 */
	private String resource;

	/**
	 * Default method type to submit request as.
	 *
	 * Is used only if the input request does not have a type specified.
	 */
	private Method method;

	/**
	 * (Optional) Default timings to use if the request does not specify
	 * timings.
	 */
	private TaskRequestTiming timings;

	public TaskRequestConverter(String resource) {
		this(resource, null, null);
	}

	public TaskRequestConverter(String resource, Method method, TaskRequestTiming timings) {
		this.setResource(resource);
		this.setMethod(method);
		this.setTimings(timings);
	}

	public String getResource() {
		return this.resource;
	}

	public void setResource(String resource) {
		if (resource != null && resource.startsWith("/") == false) {
			resource = "/" + resource;
		}

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
		TaskRequestReader reader = new TaskRequestReader(input);
		TaskOptionBuilder builder = new TaskOptionBuilder(reader);
		return builder.buildTaskOptions();
	}

	private class TaskRequestReader {

		private final TaskRequest request;

		public TaskRequestReader(TaskRequest request) {
			this.request = request;
		}

		public String getName() {
			return this.request.getName();
		}

		public String getFullUrl() {
			String url = this.request.getUrl();

			Joiner joiner = Joiner.on("/").skipNulls();
			String resourceUrl = joiner.join(TaskRequestConverter.this.resource, url);

			return resourceUrl;
		}

		public Method getMethod() {
			Method method = this.request.getMethod();

			if (method == null) {
				method = TaskRequestConverter.this.method;
			}

			return method;
		}

		public TaskRequestTiming getTiming() {
			TaskRequestTiming timing = this.request.getTimings();

			if (timing == null) {
				timing = TaskRequestConverter.this.timings;
			}

			return timing;
		}

		public Collection<TaskParameter> getHeaders() {
			return this.request.getHeaders();
		}

		public Collection<TaskParameter> getParameters() {
			return this.request.getParameters();
		}

	}

	private class TaskOptionBuilder {

		private final TaskRequestReader reader;

		public TaskOptionBuilder(TaskRequestReader reader) {
			this.reader = reader;
		}

		public TaskOptions buildTaskOptions() {
			TaskOptions options = this.newTaskOptions();
			options = this.updateTimings(options);
			options = this.appendHeaders(options);
			options = this.appendParameters(options);
			return options;
		}

		private TaskOptions newTaskOptions() {
			String url = this.reader.getFullUrl();
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

		private TaskOptions appendParameters(TaskOptions options) {
			Collection<TaskParameter> parameters = this.reader.getParameters();

			if (parameters != null) {
				for (TaskParameter pair : parameters) {
					String param = pair.getParameter();
					String value = pair.getValue();
					options = options.param(param, value);
				}
			}

			return options;
		}

		private TaskOptions appendHeaders(TaskOptions options) {
			Collection<TaskParameter> headers = this.reader.getHeaders();

			if (headers != null) {
				for (TaskParameter pair : headers) {
					String param = pair.getParameter();
					String value = pair.getValue();
					options = options.header(param, value);
				}
			}

			return options;
		}

	}

}
