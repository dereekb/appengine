package com.dereekb.gae.utilities.task;

/**
 * {@link Task} that takes in multiple elements.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public interface IterableTask<T>
        extends Task<Iterable<T>> {

}
