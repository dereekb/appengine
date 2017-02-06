package com.dereekb.gae.web.taskqueue.controller.extension.iterate.old.request.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.taskqueue.system.KeyedEncodedParameter;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.system.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.server.taskqueue.system.impl.TaskRequestImpl;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.TaskQueueIterateController;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * {@link TaskRequest} used by {@link IterateTaskContinutationImpl} to schedule
 * continuations.
 *
 * @author dereekb
 *
 */
public class IterateTaskRequestBuilder {

	/**
	 * Default format used by
	 * {@link TaskQueueIterateController#iterate(String, String, Integer, String, Map)}
	 * .
	 */
	public static final String DEFAULT_TASK_URL_FORMAT = "%s/iterate/%s";

	/**
	 * {@link Method} of built {@link TaskRequest} values.
	 */
	public Method method = Method.PUT;

	/**
	 * Custom timings.
	 */
	public TaskRequestTiming timing = null;

	/**
	 * Task format.
	 */
	public String taskUrlFormat = DEFAULT_TASK_URL_FORMAT;

	/**
	 * HTTP header name for step value.
	 */
	public String stepHeader = TaskQueueIterateController.TASK_STEP_HEADER;

	/**
	 * HTTP header name for cursor value.
	 */
	public String cursorHeader = TaskQueueIterateController.CURSOR_HEADER;

	public IterateTaskRequestBuilder() {}

	public String getTaskUrlFormat() {
		return this.taskUrlFormat;
	}

	public void setTaskUrlFormat(String taskUrlFormat) {
		this.taskUrlFormat = taskUrlFormat;
	}

	public String getStepHeader() {
		return this.stepHeader;
	}

	public void setStepHeader(String stepHeader) {
		this.stepHeader = stepHeader;
	}

	public String getCursorHeader() {
		return this.cursorHeader;
	}

	public void setCursorHeader(String cursorHeader) {
		this.cursorHeader = cursorHeader;
	}

	public Builder builder() {
		return new Builder();
	}

	public Builder builder(IterateTaskInput input) {
		return new Builder(input);
	}

	public class Builder {

		/**
		 * The model type.
		 */
		private String modelType;

		/**
		 * The task value.
		 */
		private String task;

		/**
		 * The {@link Cursor} value, if applicable.
		 */
		private Cursor cursor;

		/**
		 * The current iteration step, if applicable.
		 */
		private Integer step;

		/**
		 * Task parameters.
		 */
		private Collection<KeyedEncodedParameter> parameters;

		private Builder() {}

		private Builder(IterateTaskInput input) {
			this.updateFromInput(input);
		}

		public void updateFromInput(IterateTaskInput input) {
			this.task = input.getTaskName();
			this.modelType = input.getModelType();
			this.step = input.getIterationStep();

			if (this.step == null) {
				this.step = 0;
			} else {
				this.step += 1;
			}

			List<KeyedEncodedParameterImpl> parameters = KeyedEncodedParameterImpl.makeParametersWithMap(input.getParameters());
			this.parameters = new ArrayList<KeyedEncodedParameter>(parameters);
		}

		public String getModelType() {
			return this.modelType;
		}

		public void setModelType(String modelType) throws IllegalArgumentException {
			if (modelType == null || modelType.isEmpty()) {
				throw new IllegalArgumentException("Model type cannot be null or empty.");
			}

			this.modelType = modelType;
		}

		public String getTask() {
			return this.task;
		}

		public void setTask(String task) throws IllegalArgumentException {
			if (task == null || task.isEmpty()) {
				throw new IllegalArgumentException("Model type cannot be null or empty.");
			}

			this.task = task;
		}

		public Cursor getCursor() {
			return this.cursor;
		}

		public void setCursor(Cursor cursor) {
			this.cursor = cursor;
		}

		public Integer getStep() {
			return this.step;
		}

		public void setStep(Integer step) {
			this.step = step;
		}

		public Collection<KeyedEncodedParameter> getParameters() {
			return this.parameters;
		}

		public void setParameters(Collection<KeyedEncodedParameter> parameters) {
			this.parameters = parameters;
		}

		public TaskRequestImpl build() {
			TaskRequestImpl request = new TaskRequestImpl();

			String url = String.format(IterateTaskRequestBuilder.this.taskUrlFormat, this.modelType, this.task);
			request.setUrl(url);

			request.setTimings(IterateTaskRequestBuilder.this.timing);
			request.setMethod(IterateTaskRequestBuilder.this.method);

			Collection<KeyedEncodedParameter> headers = this.buildHeaders();
			request.setHeaders(headers);

			request.setParameters(this.parameters);

			return request;
		}

		private Collection<KeyedEncodedParameter> buildHeaders() {
			List<KeyedEncodedParameter> headers = new ArrayList<KeyedEncodedParameter>();

			if (this.cursor != null) {
				String cursorString = this.cursor.toWebSafeString();
				KeyedEncodedParameter cursorHeader = new KeyedEncodedParameterImpl(IterateTaskRequestBuilder.this.cursorHeader,
				        cursorString);
				headers.add(cursorHeader);
			}

			if (this.step != null) {
				KeyedEncodedParameter stepHeader = new KeyedEncodedParameterImpl(IterateTaskRequestBuilder.this.stepHeader, this.step);
				headers.add(stepHeader);
			}

			return headers;
		}

	}

}
