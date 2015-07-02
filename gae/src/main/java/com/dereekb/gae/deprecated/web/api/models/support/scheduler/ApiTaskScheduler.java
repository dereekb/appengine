package com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler;

import java.util.Collection;
import java.util.Collections;

import com.thevisitcompany.gae.server.taskqueue.TaskQueueManager;
import com.thevisitcompany.gae.server.taskqueue.TaskQueuePushRequest;

/**
 * API Task Scheduler that converts API requests and adds them to the Task Queue.
 * 
 * @author dereekb
 */
public class ApiTaskScheduler {

	private ApiTaskSchedulerDelegate delegate;
	private TaskQueueManager manager;

	/**
	 * Schedules the given requests with the TaskQueueManager.
	 * 
	 * @param request
	 * @return Collection of requests that were just scheduled.
	 * @throws ApiTaskUnavailableException
	 */
	public Collection<TaskQueuePushRequest> schedule(ApiTaskSchedulerRequest request)
	        throws ApiTaskUnavailableException {
		Collection<TaskQueuePushRequest> requests = this.delegate.createRequests(request);

		if (requests == null) {
			throw new ApiTaskUnavailableException("Task with name '" + request.getName() + "' could not be completed.");
		} else {
			boolean success = manager.add(requests);

			if (success == false) {
				requests = Collections.emptyList();
			}
		}

		return requests;
	}

	public ApiTaskSchedulerDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(ApiTaskSchedulerDelegate delegate) {
		this.delegate = delegate;
	}

	public TaskQueueManager getManager() {
		return manager;
	}

	public void setManager(TaskQueueManager manager) {
		this.manager = manager;
	}

}
