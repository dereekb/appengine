package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.task.config.ReadTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.ConfigurableTask;

/**
 * Task for reading models into a {@link ReadPair}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ReadTask<T extends UniqueModel>
        extends ConfigurableTask<Iterable<ReadPair<T>>, ReadTaskConfig> {

}
