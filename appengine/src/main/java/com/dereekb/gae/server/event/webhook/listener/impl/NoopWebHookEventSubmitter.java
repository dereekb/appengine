package com.dereekb.gae.server.event.webhook.listener.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitter;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;

/**
 * A {@link WebHookEventSubmitter} implementation that does nothing.
 *
 * Typically only used on development servers.
 *
 * @author dereekb
 *
 */
public class NoopWebHookEventSubmitter
        implements WebHookEventSubmitter, EventServiceListener {

	// MARK: WebHookEventSubmitter
	@Override
	public void submitEvent(WebHookEvent event) throws WebHookEventSubmitException {
		// Do nothing.
	}

	@Override
	public void handleEvent(Event event) {
		// Do nothing.
	}

}
