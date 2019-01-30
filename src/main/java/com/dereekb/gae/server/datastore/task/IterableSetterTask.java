package com.dereekb.gae.server.datastore.task;

/**
 * Implements {@link IterableStoreTask}, {@link IterableUpdateTask}, and
 * {@link IterableDeleteTask}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IterableSetterTask<T>
        extends IterableStoreTask<T>, IterableUpdateTask<T>, IterableDeleteTask<T> {

}
