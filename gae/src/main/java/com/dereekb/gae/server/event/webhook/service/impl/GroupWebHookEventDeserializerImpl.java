package com.dereekb.gae.server.event.webhook.service.impl;

import java.util.Map;

import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventDeserializer;

/**
 * Primary {@link WebHookEventDeserializer} entry point
 *
 * @author dereekb
 *
 */
public class GroupWebHookEventDeserializerImpl extends AbstractWebHookEventDeserializerImpl {

	public GroupWebHookEventDeserializerImpl(Map<String, WebHookEventDeserializer> entries) {
		super(entries);
	}

	// MARK: AbstractWebHookEventDeserializerImpl
	@Override
	protected String getEntryKeyForWebHookEvent(WebHookEvent input) {
		return input.getEventType().getEventGroupCode();
	}

	@Override
	public String toString() {
		return "TypedModelWebHookEventDeserializerImpl [getEntries()=" + this.getEntries() + "]";
	}

}
