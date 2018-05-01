package com.dereekb.gae.server.event.webhook.listener;

import com.dereekb.gae.server.event.webhook.WebHookEvent;

public interface WebHookEventSubmitterDelegate {

	void submitEvent(WebHookEvent webHookEvent);

}
