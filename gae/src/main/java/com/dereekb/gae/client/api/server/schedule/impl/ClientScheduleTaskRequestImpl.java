package com.dereekb.gae.client.api.server.schedule.impl;

import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskRequest;
import com.dereekb.gae.client.api.server.shared.ClientServerRequestImpl;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;

/**
 * {@link ClientScheduleTaskRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ClientScheduleTaskRequestImpl extends ClientServerRequestImpl
        implements ClientScheduleTaskRequest {

	private ApiScheduleTaskRequest taskRequest;

	public ClientScheduleTaskRequestImpl() {}

	public ClientScheduleTaskRequestImpl(String requestUrl, ApiScheduleTaskRequest taskRequest) {
		super(requestUrl);
		this.setTaskRequest(taskRequest);
	}

	@Override
	public ApiScheduleTaskRequest getTaskRequest() {
		return this.taskRequest;
	}

	public void setTaskRequest(ApiScheduleTaskRequest taskRequest) {
		if (taskRequest == null) {
			throw new IllegalArgumentException("taskRequest cannot be null.");
		}

		this.taskRequest = taskRequest;
	}

	@Override
	public String toString() {
		return "ClientScheduleTaskRequestImpl [taskRequest=" + this.taskRequest + "]";
	}

}
