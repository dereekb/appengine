package com.dereekb.gae.web.taskqueue.model.extension.iterate.utility;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.KeyParameterTaskRequestBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Used for building {@link TaskRequest} for {@link TaskQueueIterateController}.
 *
 * @author dereekb
 *
 */
public class TaskQueueIterateRequestBuilderUtility {

	private static final Method ITERATE_REQUEST_METHOD = Method.PUT;

	public static final TaskQueueIterateRequestBuilderUtility SINGLETON = new TaskQueueIterateRequestBuilderUtility();

	// MARK: Make
	public <T extends UniqueModel> TaskRequestBuilder<T> make(String taskName,
	                                                          String modelType) {
		return this.make(taskName, modelType, null);
	}

	public <T extends UniqueModel> TaskRequestBuilder<T> make(String taskName,
	                                                          String modelType,
	                                                          IterateType iterateType) {
		return this.make(taskName, modelType, iterateType, null);
	}

	public <T extends UniqueModel> TaskRequestBuilder<T> make(String taskName,
	                                                          String modelType,
	                                                          IterateType iterateType,
	                                                          TaskRequest request) {
		Builder<T> builder = this.builder(taskName, modelType, iterateType, request);
		return builder.make();
	}

	public <T extends UniqueModel> Builder<T> builder(String taskName,
	                                                  String modelType) {
		return this.builder(taskName, modelType, null);
	}

	public <T extends UniqueModel> Builder<T> builder(String taskName,
	                                                  String modelType,
	                                                  IterateType iterateType) {
		return this.builder(taskName, modelType, iterateType, null);
	}

	public <T extends UniqueModel> Builder<T> builder(String taskName,
	                                                  String modelType,
	                                                  IterateType iterateType,
	                                                  TaskRequest request) {
		return new Builder<T>(taskName, modelType, iterateType, request);
	}

	// MARK: Internal Builder
	public static class Builder<T extends UniqueModel> {

		private String taskName;
		private String modelType;
		private IterateType iterateType;

		private Collection<KeyedEncodedParameter> headers;
		private Collection<KeyedEncodedParameter> parameters;

		private Parameters queryParameters;

		private TaskRequestTiming timings;

		public Builder(String taskName, String modelType) {
			this(taskName, modelType, IterateType.SEQUENCE);
		}

		public Builder(String taskName, String modelType, IterateType iterateType) {
			this(taskName, modelType, iterateType, null);
		}

		public Builder(String taskName, String modelType, IterateType iterateType, TaskRequest request) {
			super();
			this.setTaskName(taskName);
			this.setModelType(modelType);
			this.setIterateType(ValueUtility.defaultTo(iterateType, IterateType.SEQUENCE));

			if (request != null) {
				this.setHeaders(request.getHeaders());
				this.setParameters(request.getParameters());
				this.setTimings(request.getTimings());
			}
		}

		// MARK: Configure
		public String getTaskName() {
			return this.taskName;
		}

		public void setTaskName(String taskName) {
			if (StringUtility.isEmptyString(taskName)) {
				throw new IllegalArgumentException("taskName cannot be null.");
			}

			this.taskName = taskName;
		}

		public String getModelType() {
			return this.modelType;
		}

		public void setModelType(String modelType) {
			if (StringUtility.isEmptyString(modelType)) {
				throw new IllegalArgumentException("modelType cannot be null.");
			}

			this.modelType = modelType;
		}

		public IterateType getIterateType() {
			return this.iterateType;
		}

		public void setIterateType(IterateType iterateType) {
			if (iterateType == null) {
				throw new IllegalArgumentException("iterateType cannot be null.");
			}

			this.iterateType = iterateType;
		}

		public Collection<KeyedEncodedParameter> getHeaders() {
			return this.headers;
		}

		public void setHeaders(Collection<KeyedEncodedParameter> headers) {
			this.headers = headers;
		}

		public Collection<KeyedEncodedParameter> getParameters() {
			return this.parameters;
		}

		public void setParameters(Collection<KeyedEncodedParameter> parameters) {
			this.parameters = parameters;
		}

		public Parameters getQueryParameters() {
			return this.queryParameters;
		}

		public void setQueryParameters(Parameters queryParameters) {
			this.queryParameters = queryParameters;
		}

		public TaskRequestTiming getTimings() {
			return this.timings;
		}

		public void setTimings(TaskRequestTiming timings) {
			this.timings = timings;
		}

		// MARK: Build
		public TaskRequestBuilder<T> make() {
			String url = this.iterateType.pathForTask(this.modelType, this.taskName);
			TaskRequestImpl request = new TaskRequestImpl(url, ITERATE_REQUEST_METHOD);

			Collection<? extends KeyedEncodedParameter> parameters = this.getParameters();

			if (this.queryParameters != null) {
				List<KeyedEncodedParameterImpl> keyedQueryParameters = KeyedEncodedParameterImpl
				        .makeParametersWithMap(this.queryParameters.getParameters());
				parameters = KeyedEncodedParameterImpl.merge(parameters, keyedQueryParameters);
			}

			request.setHeaders(this.headers);
			request.setParameters(parameters);
			request.setTimings(this.timings);

			return new KeyParameterTaskRequestBuilder<T>(request);
		}

		@Override
		public String toString() {
			return "Builder [taskName=" + this.taskName + ", modelType=" + this.modelType + ", iterateType="
			        + this.iterateType + ", headers=" + this.headers + ", parameters=" + this.parameters
			        + ", queryParameters=" + this.queryParameters + ", timings=" + this.timings + "]";
		}

	}

	@Override
	public String toString() {
		return "TaskQueueIterateRequestBuilderUtility []";
	}

}
