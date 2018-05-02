package com.dereekb.gae.server.event.webhook.listener.impl;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskService;
import com.dereekb.gae.client.api.server.schedule.impl.ClientScheduleTaskRequestImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitterDelegate;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.web.api.server.schedule.impl.ApiScheduleTaskRequestImpl;

/**
 * {@link WebHookEventSubmitterDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class WebHookEventSubmitterDelegateImpl extends AbstractWebHookEventSubmitterDelegate {

	private ClientScheduleTaskService scheduleTaskService;

	public WebHookEventSubmitterDelegateImpl(TaskScheduler scheduler, ClientScheduleTaskService scheduleTaskService) {
		super(scheduler);
		this.setScheduleTaskService(scheduleTaskService);
	}

	public ClientScheduleTaskService getScheduleTaskService() {
		return this.scheduleTaskService;
	}

	public void setScheduleTaskService(ClientScheduleTaskService scheduleTaskService) {
		if (scheduleTaskService == null) {
			throw new IllegalArgumentException("scheduleTaskService cannot be null.");
		}

		this.scheduleTaskService = scheduleTaskService;
	}

	// MARK: WebHookEventSubmitterDelegate
	@Override
	protected void trySubmitEvent(WebHookEvent event) throws WebHookEventSubmitException {
		try {
			ApiScheduleTaskRequestImpl request = new ApiScheduleTaskRequestImpl();
			request.setData(event.getJsonNode());

			ClientScheduleTaskRequestImpl clientRequest = new ClientScheduleTaskRequestImpl(request);
			this.scheduleTaskService.scheduleTask(clientRequest, ClientRequestSecurityImpl.systemSecurity());
		} catch (ClientRequestFailureException e) {
			throw new WebHookEventSubmitException(e);
		}
	}

	@Override
	public String toString() {
		return "WebHookEventSubmitterDelegateImpl [scheduleTaskService=" + this.scheduleTaskService + "]";
	}

}
