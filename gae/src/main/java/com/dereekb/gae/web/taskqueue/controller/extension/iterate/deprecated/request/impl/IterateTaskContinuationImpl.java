package com.dereekb.gae.web.taskqueue.controller.extension.iterate.old.request.impl;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestSystem;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.deprecated.request.IterateTaskContinuation;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link IterateTaskContinuation} implementation.
 *
 * @author dereekb
 *
 */
public class IterateTaskContinuationImpl
        implements IterateTaskContinuation {

	/**
	 * {@link TaskRequestSystem} to submit the task to.
	 */
	private TaskRequestSystem system;
	private IterateTaskRequestBuilder taskRequestBuilder = new IterateTaskRequestBuilder();

	public IterateTaskContinuationImpl(TaskRequestSystem system) {
		this.setSystem(system);
	}

	public TaskRequestSystem getSystem() {
		return this.system;
	}

	public void setSystem(TaskRequestSystem system) throws IllegalArgumentException {
		if (system == null) {
			throw new IllegalArgumentException("System cannot be null.");
		}

		this.system = system;
	}

	public IterateTaskRequestBuilder getTaskRequestBuilder() {
		return this.taskRequestBuilder;
	}

	public void setTaskRequestBuilder(IterateTaskRequestBuilder taskRequestBuilder) {
		this.taskRequestBuilder = taskRequestBuilder;
	}

	// MARK: Iterate Task Continuation
	@Override
	public void continueTask(IterateTaskInput input,
	                         Cursor cursor) {
		TaskRequest request = this.buildRequest(input, cursor);
		this.system.submitRequest(request);
	}

	private TaskRequest buildRequest(IterateTaskInput input,
	                                 Cursor cursor) {
		IterateTaskRequestBuilder.Builder builder = this.taskRequestBuilder.builder(input);
		builder.setCursor(cursor);
		return builder.build();
	}

}
