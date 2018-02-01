package com.dereekb.gae.client.api.server.schedule;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskController;

/**
 * Client interface for interacting with the {@link ApiScheduleTaskController}.
 *
 * @author dereekb
 *
 */
public interface ClientScheduleTaskService {

	/**
	 * Schedules the task on the remote server.
	 *
	 * @param request
	 *            {@link ClientScheduleTaskRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}. Never {@code null}.
	 * @return {@link ClientScheduleTaskResponse}. Never {@code null}.
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientScheduleTaskResponse scheduleTask(ClientScheduleTaskRequest request,
	                                               ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientRequestFailureException;

}
