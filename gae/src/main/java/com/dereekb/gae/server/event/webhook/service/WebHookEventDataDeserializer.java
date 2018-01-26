package com.dereekb.gae.server.event.webhook.service;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.WebHookEventData;

/**
 * {@link SingleDirectionalConverter} for deserializing a {@link WebHookEvent}
 * to an {@link Event}.
 *
 * @author dereekb
 *
 */
public interface WebHookEventDataDeserializer
        extends SingleDirectionalConverter<WebHookEventData, EventData> {

}
