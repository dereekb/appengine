package com.dereekb.gae.server.event.webhook.service.impl;

import java.util.Map;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.service.WebHookEventSerializer;

/**
 * Primary {@link WebHookEventSerializer} that delegates serialization to other
 * entries based on the group of the input {@link Event}.
 *
 * @author dereekb
 *
 */
public class GroupWebHookEventSerializerImpl extends AbstractWebHookEventSerializerImpl
        implements WebHookEventSerializer {

	public GroupWebHookEventSerializerImpl(Map<String, WebHookEventSerializer> entries) {
		super(entries);
	}

	// MARK: WebHookEventSerializer
	@Override
	protected String getEntryKeyForEvent(Event input) {
		return input.getEventType().getEventGroupCode();
	}

	@Override
	public String toString() {
		return "GroupWebHookEventSerializerImpl [getEntries()=" + this.getEntries() + "]";
	}

}
