package com.dereekb.gae.server.event.model.shared.webhook;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.webhook.service.WebHookEventConverter;

/**
 * {@link WebHookEventConverter} extension of
 * {@link ModelWebHookEventSerializer}.s
 *
 * @author dereekb
 *
 */
public interface ModelWebHookEventConverter<T extends UniqueModel>
        extends ModelWebHookEventSerializer<T>, WebHookEventConverter {}
