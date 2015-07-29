package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * {@link IterableTask} for creating models using {@link CreatePair} instances.
 *
 * @author dereekb
 *
 */
public interface CreatePairTask<T>
        extends IterableTask<CreatePair<T>> {

}
