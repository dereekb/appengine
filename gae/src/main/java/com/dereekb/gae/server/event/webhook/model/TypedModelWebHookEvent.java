package com.dereekb.gae.server.event.webhook.model;

import java.util.List;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;
import com.dereekb.gae.server.event.webhook.WebHookEvent;

/**
 * {@link WebHookEvent} for a specific model type.
 *
 * @author dereekb
 *
 * @deprecated Use {@link ModelKeyEventData} instead.
 */
@Deprecated
public abstract interface TypedModelWebHookEvent
        extends WebHookEvent, TypedModel {

	/**
	 * Returns the list of model keys for this event.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> getModelKeys();

}
