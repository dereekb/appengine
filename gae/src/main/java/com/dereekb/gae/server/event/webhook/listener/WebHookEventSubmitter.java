package com.dereekb.gae.server.event.webhook.listener;

import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;

/**
 * Service used to submit {@link WebHookEvent} values to a remote server for
 * handling.
 *
 * @author dereekb
 *
 */
public interface WebHookEventSubmitter {

	/**
	 * Submits the event to the remote server.
	 *
	 * @param event
	 *            {@link WebHookEvent}. Never {@code null}.
	 * @throws WebHookEventSubmitException
	 *             thrown if the event fails being submitted.
	 */
	public void submitEvent(WebHookEvent event) throws WebHookEventSubmitException;

}
