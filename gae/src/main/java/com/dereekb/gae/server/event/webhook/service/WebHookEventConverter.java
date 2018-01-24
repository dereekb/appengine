package com.dereekb.gae.server.event.webhook.service;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.WebHookEvent;

public interface WebHookEventConverter
        extends WebHookEventSerializer, BidirectionalConverter<Event, WebHookEvent> {}
