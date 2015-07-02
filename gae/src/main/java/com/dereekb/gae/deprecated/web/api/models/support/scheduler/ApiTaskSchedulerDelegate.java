package com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler;

import java.util.Collection;

import com.thevisitcompany.gae.server.taskqueue.TaskQueuePushRequest;

/**
 * Delegate for converting {@link ApiTaskSchedulerRequest} objects.
 * 
 * @author dereekb
 */
public interface ApiTaskSchedulerDelegate {

	/**
	 * Converts a single {@link ApiTaskSchedulerRequest} into one or more TaskQueuePushRequests.
	 * 
	 * If the task named is not valid, then the function returns null.
	 * 
	 * @param request
	 * @return Collection of TaskQueuePushRequests. Null if not applicable.
	 */
	public Collection<TaskQueuePushRequest> createRequests(ApiTaskSchedulerRequest request)
	        throws ApiTaskUnavailableException;

}
