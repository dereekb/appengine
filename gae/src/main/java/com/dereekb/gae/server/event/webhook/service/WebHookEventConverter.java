package com.dereekb.gae.server.event.webhook.service;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.WebHookEvent;

/**
 * Bidirectional {@link WebHookEventSerializer} that also serializes from a
 * WebHookEvent.
 *
 * @author dereekb
 *
 */
public interface WebHookEventConverter
        extends WebHookEventSerializer, BidirectionalConverter<Event, WebHookEvent> {

	public Event convertSingle(WebHookEvent input) throws ConversionFailureException;

}
