package com.dereekb.gae.server.event.model.shared.webhook;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;
import com.dereekb.gae.server.event.webhook.WebHookEventData;

/**
 * {@link SingleDirectionalConverter} for deserializing a
 * {@link WebHookEventData} to an {@link ModelEventData}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelWebHookEventDataDeserializer<T extends UniqueModel>
        extends SingleDirectionalConverter<WebHookEventData, ModelEventData<T>> {}
