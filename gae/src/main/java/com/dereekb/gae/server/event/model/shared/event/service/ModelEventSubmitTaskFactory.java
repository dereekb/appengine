package com.dereekb.gae.server.event.model.shared.event.service;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.ModelKeyListAccessorTask;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.service.task.ModelEventSubscriptionTask;

/**
 * Used for building {@link ModelKeyListAccessorTask}'s that take in models of a
 * certain type then subscribes them.
 *
 * @author dereekb
 *
 */
public interface ModelEventSubmitTaskFactory<T extends UniqueModel> {

	/**
	 * Creates a new {@link ModelEventSubscriptionTask}.
	 *
	 * @param event
	 *            {@link EventType}. Never {@code null}.
	 * @return {@link ModelKeyListAccessorTask}. Never {@code null}.
	 */
	public ModelEventSubscriptionTask<T> makeSubmitEventTask(EventType event);

}
