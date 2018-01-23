package com.dereekb.gae.server.event.model.shared.event.service.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.ModelKeyListAccessorTask;

/**
 * {@link ModelKeyListAccessorTask} that submits {@link Event}s for the input
 * models.
 * <p>
 * Should generally be used only in the task queue.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelEventSubscriptionTask<T extends UniqueModel>
        extends ModelKeyListAccessorTask<T> {

}
