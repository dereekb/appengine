package com.dereekb.gae.server.event.model.shared.webhook.impl;

import java.util.Map;

import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventDeserializer;
import com.dereekb.gae.server.event.model.shared.webhook.impl.utility.WebHookEventDataDeserializerUtility;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.WebHookEventData;
import com.dereekb.gae.server.event.webhook.service.WebHookEventDeserializer;
import com.dereekb.gae.server.event.webhook.service.impl.AbstractWebHookEventDeserializerImpl;

/**
 * Primary {@link WebHookEventDeserializer} for model events that delegates
 * deserialization to other {@link ModelWebHookEventDeserializer}.
 *
 * @author dereekb
 *
 */
public class TypedModelWebHookEventDeserializerImpl extends AbstractWebHookEventDeserializerImpl {

	public TypedModelWebHookEventDeserializerImpl(Map<String, ? extends ModelWebHookEventDeserializer<?>> entries) {
		super(entries);
	}

	// MARK: AbstractWebHookEventDeserializerImpl
	@Override
	protected String getEntryKeyForWebHookEvent(WebHookEvent input) {
		WebHookEventData webHookEventData = input.getEventData();
		WebHookEventDataDeserializerUtility utility = new WebHookEventDataDeserializerUtility(webHookEventData);
		return utility.getModelType();
	}

	@Override
	public String toString() {
		return "TypedModelWebHookEventDeserializerImpl [getEntries()=" + this.getEntries() + "]";
	}

}
