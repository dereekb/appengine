package com.dereekb.gae.server.event.webhook.listener;

import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;

/**
 * {@link WebHookEventSubmitter} delegate.
 *
 * @author dereekb
 *
 */
public interface WebHookEventSubmitterDelegate {

	/**
	 *
	 * @param webHookEvent
	 *            {@link WebHookEvent}. Never {@code null}.
	 */
	public void submitEvent(WebHookEvent webHookEvent) throws WebHookEventSubmitException;

	/**
	 *
	 * @param webHookEvent
	 *            {@link WebHookEvent}. Never {@code null}.
	 * @param scheduleRetryOnFailure
	 *            whether or not to schedule a retry if submission fails, or to
	 *            throw the exception.
	 */
	public void submitEvent(WebHookEvent webHookEvent,
	                        boolean scheduleRetryOnFailure)
	        throws WebHookEventSubmitException;

}
