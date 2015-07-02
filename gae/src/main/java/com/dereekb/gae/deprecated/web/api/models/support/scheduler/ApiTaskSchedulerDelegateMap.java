package com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler;

import java.util.Collection;

import com.thevisitcompany.gae.server.taskqueue.TaskQueuePushRequest;
import com.thevisitcompany.gae.utilities.collections.map.CatchMap;

/**
 * Map of {@link ApiTaskSchedulerDelegate} objects, mapped to the task(s) they should respond to.
 * 
 * @author dereekb
 */
public class ApiTaskSchedulerDelegateMap extends CatchMap<ApiTaskSchedulerDelegate>
        implements ApiTaskSchedulerDelegate {

	@Override
	public Collection<TaskQueuePushRequest> createRequests(ApiTaskSchedulerRequest request)
	        throws ApiTaskUnavailableException {

		String requestName = request.getName();
		ApiTaskSchedulerDelegate delegate = this.get(requestName);
		Collection<TaskQueuePushRequest> taskQueueRequests = null;

		if (delegate != null) {
			taskQueueRequests = delegate.createRequests(request);
		} else {
			throw new ApiTaskUnavailableException("No delegate available to handle task named: " + requestName);
		}

		return taskQueueRequests;
	}

}
