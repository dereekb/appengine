package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Task for creating elements.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface CreateTask<T extends UniqueModel>
        extends AtomicTask<CreatePair<T>, CreateTaskConfig> {

}
