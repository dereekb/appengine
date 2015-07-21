package com.dereekb.gae.model.extension.taskqueue.iterate.impl;

import com.dereekb.gae.model.extension.taskqueue.iterate.IterateTaskContinuation;
import com.dereekb.gae.model.extension.taskqueue.iterate.IterateTaskInput;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestSystem;
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

	// MARK: Iterate Task Continuation
	@Override
	public void continueTask(IterateTaskInput input,
	                         Cursor cursor) {
		TaskRequest request = this.buildRequest(input, cursor);
		this.system.submitRequest(request);
	}

	private TaskRequest buildRequest(IterateTaskInput input,
	                                 Cursor cursor) {
		// TODO Build the continuation task.
		return null;
	}

}
