package com.dereekb.gae.server.event.model.shared.webhook;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventDeserializer;

/**
 * Used for serializing a {@link ModelEvent} and {@link ModelKeyEvent} instances
 * from a {@link WebHookEvent}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelWebHookEventDeserializer<T extends UniqueModel>
        extends WebHookEventDeserializer {}
