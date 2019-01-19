package com.dereekb.gae.server.event.webhook.listener.impl;

import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitterDelegate;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;
import com.dereekb.gae.server.event.webhook.service.WebHookEventConverter;

/**
 * {@link WebHookEventSubmitterDelegate} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
@SuppressWarnings("unused")
public class LocalWebHookEventSubmitterDelegateImpl
        implements WebHookEventSubmitterDelegate {

	private WebHookEventConverter converter;

	// MARK: WebHookEventSubmitterDelegate
	@Override
	public void submitEvent(WebHookEvent webHookEvent) throws WebHookEventSubmitException {
		// TODO Auto-generated method stub

	}

	@Override
	public void submitEvent(WebHookEvent webHookEvent,
	                        boolean scheduleRetryOnFailure)
	        throws WebHookEventSubmitException {
		// TODO Auto-generated method stub

	}

}
