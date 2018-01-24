package com.dereekb.gae.server.event.webhook.service;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.WebHookEvent;

/**
 * {@link DirectionalConverter} for {@link Event} values.
 *
 * @author dereekb
 *
 */
public interface WebHookEventSerializer
        extends DirectionalConverter<Event, WebHookEvent> {}
