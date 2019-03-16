package com.dereekb.gae.server.event.model.shared.event;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.event.event.Event;

/**
 * {@link Event} with {@link ModelKeyEventData}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelKeyEvent
        extends TypedModel, Event {

	public static final String MODEL_EVENT_GROUP = "model";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModelKeyEventData getEventData();

}
