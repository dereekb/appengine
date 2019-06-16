package com.dereekb.gae.server.taskqueue.scheduler.utility.converter.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestConverter;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestHost;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestReader;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;

/**
 * Used for converting {@link TaskRequest} to {@link TaskOption}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractTaskRequestConverterImpl<T> extends AbstractDirectionalConverter<TaskRequest, T>
        implements TaskRequestConverter<T> {

	private static final SimplePath DEFAULT_RESOURCE = new SimplePathImpl("/taskqueue");
	private static final String HOST_HEADER = "Host";

	private boolean shouldAssertPathNotEmpty = true;

	/**
	 * The base system SimplePath/resource to submit the task to.
	 */
	private SimplePath resource = DEFAULT_RESOURCE;

	/**
	 * (Optional) Default timings to use if the request does not specify
	 * timings.
	 */
	private TaskRequestTiming timings;

	/**
	 * (Optional) Host to send the task to.
	 */
	private TaskRequestHost host;

	public AbstractTaskRequestConverterImpl() {
		this(DEFAULT_RESOURCE, null, null);
	}

	public AbstractTaskRequestConverterImpl(String url) {
		this(new SimplePathImpl(url), null, null);
	}

	public AbstractTaskRequestConverterImpl(SimplePath resource) {
		this(resource, null, null);
	}

	public AbstractTaskRequestConverterImpl(SimplePath resource, TaskRequestTiming timings) {
		this(resource, timings, null);
	}

	public AbstractTaskRequestConverterImpl(SimplePath resource,
	        TaskRequestTiming timings,
	        TaskRequestHost host) {
		this.setResource(resource);
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

	public TaskRequestTiming getTimings() {
		return this.timings;
	}

	public void setTimings(TaskRequestTiming timings) {
		this.timings = timings;
	}

	public TaskRequestHost getHost() {
		return this.host;
	}

	public void setHost(TaskRequestHost host) {
		this.host = host;
	}

	@Override
	public abstract T convertSingle(TaskRequest input) throws ConversionFailureException;

	@Override
	public TaskRequestReader makeReader(TaskRequest request) {
		return new TaskRequestReaderImpl(request);
	}

	protected final class TaskRequestReaderImpl
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

			if (AbstractTaskRequestConverterImpl.this.resource != null) {
				path = AbstractTaskRequestConverterImpl.this.resource.append(path);
			}

			return path.getPath();
		}


		@Override
		public TaskRequestTiming getTiming() {
			TaskRequestTiming timing = this.request.getTimings();

			if (timing == null) {
				timing = AbstractTaskRequestConverterImpl.this.timings;
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

		public TaskRequestHost getHost() {
			TaskRequestHost host = this.request.getHost();

			if (host == null) {
				host = AbstractTaskRequestConverterImpl.this.host;
			}

			return host;
		}

	}

}
