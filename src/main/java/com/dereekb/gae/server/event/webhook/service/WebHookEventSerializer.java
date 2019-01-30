package com.dereekb.gae.server.event.webhook.service;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.WebHookEvent;

/**
 * {@link DirectionalConverter} for {@link Event} values.
 * <p>
 * Is allowed to freely throw {@link ConversionFailureException} if it isn't
 * responsible for serializing that type of event.
 *
 * @author dereekb
 *
 */
public interface WebHookEventSerializer
        extends SingleDirectionalConverter<Event, WebHookEvent>, DirectionalConverter<Event, WebHookEvent> {}
