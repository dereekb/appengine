package com.dereekb.gae.client.api.server.schedule;

import com.dereekb.gae.client.api.server.shared.ClientServerRequest;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;

/**
 * Client request for scheduling requests.
 *
 * @author dereekb
 *
 */
public interface ClientScheduleTaskRequest
        extends ClientServerRequest {

	/**
	 * Returns the task request.
	 *
	 * @return {@link ApiScheduleTaskRequest}. Never {@code null}.
	 */
	public ApiScheduleTaskRequest getTaskRequest();

}
