package com.dereekb.gae.server.datastore.task.impl;

import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.task.IterableStoreTask;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableStoreTask} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IterableStoreTaskImpl<T>
        implements IterableStoreTask<T>, IterableTask<T> {

	private Storer<T> storer;

	public IterableStoreTaskImpl(Storer<T> storer) {
		this.setStorer(storer);
	}

	public Storer<T> getStorer() {
		return this.storer;
	}

	public void setStorer(Storer<T> storer) {
		if (storer == null) {
			throw new IllegalArgumentException("Storer cannot be null.");
		}

		this.storer = storer;
	}

	// MARK: IterableStoreTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		this.doStoreTask(input);
	}

	@Override
	public void doStoreTask(Iterable<T> input) throws FailedTaskException {
		this.storer.store(input);
	}

	@Override
	public String toString() {
		return "IterableStoreTaskImpl [storer=" + this.storer + "]";
	}

}
