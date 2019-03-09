package com.dereekb.gae.server.datastore.task.impl;

import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.task.IterableUpdateTask;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableUpdateTask} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IterableUpdateTaskImpl<T>
        implements IterableUpdateTask<T>, IterableTask<T> {

	private Updater<T> updater;

	public IterableUpdateTaskImpl(Updater<T> updater) {
		this.setUpdater(updater);
	}

	public Updater<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}

		this.updater = updater;
	}

	// MARK: IterableUpdateTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		this.doUpdateTask(input);
	}

	@Override
	public void doUpdateTask(Iterable<T> input) throws FailedTaskException {
		this.updater.update(input);
	}

	@Override
	public String toString() {
		return "IterableUpdateTaskImpl [updater=" + this.updater + "]";
	}

}
