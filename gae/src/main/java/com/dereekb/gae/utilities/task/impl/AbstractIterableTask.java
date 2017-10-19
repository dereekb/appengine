package com.dereekb.gae.utilities.task.impl;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Abstract {@link IterableTask} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractIterableTask<T>
        implements IterableTask<T> {

	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		for (T task : input) {
			this.doTaskOnObject(task);
		}
	}

	protected abstract void doTaskOnObject(T input);

}
