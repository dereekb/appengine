package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * {@link IterableTask} for reading models using {@link ReadPair} instances.
 *
 * @author dereekb
 *
 */
public interface ReadPairTask<T extends UniqueModel>
        extends IterableTask<ReadPair<T>> {

}