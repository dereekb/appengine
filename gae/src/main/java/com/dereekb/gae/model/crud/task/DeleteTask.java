package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.ConfigurableTask;

/**
 * Task for deleting models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface DeleteTask<T extends UniqueModel>
        extends ConfigurableTask<Iterable<DeletePair<T>>, DeleteTaskConfig> {

}
