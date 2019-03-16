package com.dereekb.gae.server.event.model.shared.event;

import java.util.List;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.event.EventData;

/**
 * {@link EventData} for {@link Unique} models.
 *
 * @author dereekb
 *
 */
public interface ModelKeyEventData
        extends TypedModel, EventData {

	public static final String EVENT_DATA_TYPE = "model_key";

	/**
	 * Returns the list of model keys.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> getEventModelKeys();

}
