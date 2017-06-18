package com.dereekb.gae.server.datastore.models.keys.accessor.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.Task;

/**
 * {@link Task} that takes in {@link ModelKeyListAccessor} as the input. Is used
 * throughout the TaskQueue.
 * <p>
 * It is ok for models to extend/implement this type, but variables of this type
 * should instead have {@code Task<ModelKeyListAccessor<T>>} instead.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelKeyListAccessorTask<T extends UniqueModel>
        extends Task<ModelKeyListAccessor<T>> {

}
