package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.ConfigurableTask;

/**
 * Task for updating models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateTask<T extends UniqueModel>
        extends ConfigurableTask<Iterable<UpdatePair<T>>, UpdateTaskConfig> {

}
