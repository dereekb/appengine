package com.dereekb.gae.utilities.task.impl;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Abstract {@link IterableTask} that performs one task before passing through
 * to the next.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class PassthroughIterableTask<T>
        implements IterableTask<T> {

	private IterableTask<T> task;

	public PassthroughIterableTask() {}

	public PassthroughIterableTask(IterableTask<T> task) throws IllegalArgumentException {
		this.setTask(task);
	}

	public IterableTask<T> getTask() {
		return this.task;
	}

	public void setTask(IterableTask<T> task) throws IllegalArgumentException {
		if (task == null) {
			throw new IllegalArgumentException("task cannot be null.");
		}

		this.task = task;
	}

	// MARK: IterableTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		this.doPassthroughTask(input);
		this.task.doTask(input);
	}

	protected abstract void doPassthroughTask(Iterable<T> input) throws FailedTaskException;

}
