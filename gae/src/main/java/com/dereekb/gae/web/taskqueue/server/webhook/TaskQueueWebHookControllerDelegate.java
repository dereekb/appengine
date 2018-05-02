package com.dereekb.gae.web.taskqueue.server.webhook;

import com.dereekb.gae.server.event.event.service.exception.EventServiceException;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitter;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;

/**
 * {@link TaskQueueWebHookController} delegate.
 *
 * @author dereekb
 *
 */
public interface TaskQueueWebHookControllerDelegate {

	/**
	 * Submits a {@link WebHookEvent} into the system.
	 *
	 * @param webHookEvent
	 *            {@link WebHookEvent}. Never {@code null}.
	 * @throws EventServiceException
	 *             thrown if event service encounters an error.
	 */
	public void processEvent(WebHookEvent webHookEvent) throws EventServiceException;

	/**
	 * Uses a {@link WebHookEventSubmitter} to attempt to resubmit a message.
	 *
	 * @param webHookEvent
	 *            {@link WebHookEvent}. Never {@code null}.
	 * @throws WebHookEventSubmitException
	 *             thrown if re-submission fails
	 */
	public void resubmitEvent(WebHookEvent webHookEvent) throws WebHookEventSubmitException;

}
