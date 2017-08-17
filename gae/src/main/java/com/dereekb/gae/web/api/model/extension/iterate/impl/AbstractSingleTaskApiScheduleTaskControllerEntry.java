package com.dereekb.gae.web.api.model.extension.iterate.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

/**
 * Abstract {@link ApiScheduleTaskControllerEntry} implementation that always
 * makes a single request instead of a list of requests.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractSingleTaskApiScheduleTaskControllerEntry
        implements ApiScheduleTaskControllerEntry {

	// MARK: ApiScheduleTaskControllerEntry
	@Override
	public final List<? extends TaskRequest> makeTaskRequests(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {
		List<TaskRequest> requests = new ArrayList<TaskRequest>(1);

		requests.add(this.makeTaskRequest(request));

		return requests;
	}

	public abstract TaskRequest makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException;

}
