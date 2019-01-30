package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Task for updating models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateTask<T extends UniqueModel>
        extends AtomicTask<UpdatePair<T>, UpdateTaskConfig> {

}
