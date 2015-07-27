package com.dereekb.gae.utilities.task.impl;

import com.dereekb.gae.utilities.task.IterableTask;

/**
 * {@link MultiTask} extension that implements {@link IterableTask}.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public class MultiIterableTask<T> extends MultiTask<Iterable<T>>
        implements IterableTask<T> {

}
