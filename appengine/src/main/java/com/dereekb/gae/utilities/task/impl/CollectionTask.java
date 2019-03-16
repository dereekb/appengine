package com.dereekb.gae.utilities.task.impl;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} implementation that takes in a {@link Iterable} collection of
 * models, and performs the same {@link Task} on each one.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public class CollectionTask<T>
        implements IterableTask<T> {

	private Task<T> task;

	public CollectionTask() {}

	public CollectionTask(Task<T> task) {
		this.task = task;
	}

	public Task<T> getTask() {
		return this.task;
	}

	public void setTask(Task<T> task) {
		this.task = task;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		try {
			for (T element : input) {
				this.task.doTask(element);
			}
		} catch (Exception e) {
			throw new FailedTaskException("Failed iterating through all elements.", e);
		}
	}

	@Override
	public String toString() {
		return "CollectionTask [task=" + this.task + "]";
	}

}
