package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * {@link IterableTask} for creating models using {@link DeletePair} instances.
 *
 * @author dereekb
 *
 */
public interface DeletePairTask<T>
        extends IterableTask<DeletePair<T>> {

}
