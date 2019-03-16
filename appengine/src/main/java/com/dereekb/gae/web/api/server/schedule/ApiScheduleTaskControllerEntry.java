package com.dereekb.gae.web.api.server.schedule;

import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

/**
 * {@link ApiScheduleTaskController} implementation.
 * 
 * @author dereekb
 *
 */
public interface ApiScheduleTaskControllerEntry {

	/**
	 * Creates task requests for the input.
	 * <p>
	 * The exceptions produced by this request are left to the implementation.
	 * 
	 * @param request {@link ApiScheduleTaskRequest}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * 
	 * @throws MultiKeyedInvalidAttributeException thrown if multiple attributes fail.
	 * @throws KeyedInvalidAttributeException thrown if at least one attribute fails.
	 * @throws IllegalArgumentException thrown if the request is bad in general.
	 */
	public List<? extends TaskRequest> makeTaskRequests(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException;

}
