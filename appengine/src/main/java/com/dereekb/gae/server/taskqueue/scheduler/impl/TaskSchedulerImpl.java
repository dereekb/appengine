package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.SecuredTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerAuthenticator;
import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerEnqueuer;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;

/**
 * {@link TaskSchedulerContext} implementation.
 *
 * @author dereekb
 *
 */
public class TaskSchedulerImpl
        implements TaskScheduler {

	private TaskSchedulerEnqueuer enqueuer;
	private TaskSchedulerAuthenticator authenticator;

	public TaskSchedulerImpl(TaskSchedulerEnqueuer enqueuer, TaskSchedulerAuthenticator authenticator) {
		this.setEnqueuer(enqueuer);
		this.setAuthenticator(authenticator);
	}

	public TaskSchedulerEnqueuer getEnqueuer() {
		return this.enqueuer;
	}

	public void setEnqueuer(TaskSchedulerEnqueuer enqueuer) {
		if (enqueuer == null) {
			throw new IllegalArgumentException("enqueuer cannot be null.");
		}

		this.enqueuer = enqueuer;
	}

	public TaskSchedulerAuthenticator getAuthenticator() {
		return this.authenticator;
	}

	public void setAuthenticator(TaskSchedulerAuthenticator authenticator) {
		if (authenticator == null) {
			throw new IllegalArgumentException("authenticator cannot be null.");
		}

		this.authenticator = authenticator;
	}

	// MARK: TaskScheduler
	@Override
	public void schedule(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException {
		this.schedule(SingleItem.withValue(request));
	}

	@Override
	public void schedule(Collection<? extends TaskRequest> requests)
	        throws SubmitTaskException,
	            TaskAlreadyExistsException {
		List<TaskRequest> requestList = new ArrayList<TaskRequest>(requests);
		List<SecuredTaskRequest> secureRequests = this.authenticateRequests(requestList);
		this.enqueuer.enqueue(secureRequests);
	}

	// MARK: Internal
	private List<SecuredTaskRequest> authenticateRequests(List<TaskRequest> requests) {
		TaskSchedulerAuthenticator authenticator = this.getAuthenticator();
		return authenticator.authenticateRequests(requests);
	}

	@Override
	public String toString() {
		return "TaskSchedulerImpl [enqueuer=" + this.enqueuer + ", authenticator=" + this.authenticator + "]";
	}

}
