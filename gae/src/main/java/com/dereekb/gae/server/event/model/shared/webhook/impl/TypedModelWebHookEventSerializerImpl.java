package com.dereekb.gae.server.event.model.shared.webhook.impl;

import java.util.Map;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventSerializer;
import com.dereekb.gae.server.event.webhook.service.WebHookEventSerializer;
import com.dereekb.gae.server.event.webhook.service.impl.AbstractWebHookEventSerializerImpl;

/**
 * Primary {@link WebHookEventSerializer} for model events that delegates
 * serialization to other {@link ModelWebHookEventSerializer}.
 *
 * @author dereekb
 *
 */
public class TypedModelWebHookEventSerializerImpl extends AbstractWebHookEventSerializerImpl
        implements WebHookEventSerializer {

	public TypedModelWebHookEventSerializerImpl(Map<String, ? extends ModelWebHookEventSerializer<?>> entries) {
		super(entries);
	}

	// MARK: WebHookEventSerializer
	@Override
	protected String getEntryKeyForEvent(Event input) {
		EventData data = input.getEventData();

		if (data.getEventDataType() == ModelEventData.EVENT_DATA_TYPE) {
			return ((ModelEventData<?>) data).getModelType();
		} else {
			throw new IllegalArgumentException("Only events with ModelEventData can be serialized by this type.");
		}
	}

	@Override
	public String toString() {
		return "TypedModelWebHookEventSerializerImpl [getEntries()=" + this.getEntries() + "]";
	}

}
