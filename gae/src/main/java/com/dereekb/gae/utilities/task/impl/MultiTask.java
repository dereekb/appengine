package com.dereekb.gae.utilities.task.impl;

import java.util.List;

import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} implementation that runs multiple tasks on a single input value.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public class MultiTask<T>
        implements Task<T> {

	private List<Task<T>> tasks;

	public MultiTask() {}

	public MultiTask(List<Task<T>> tasks) throws IllegalArgumentException {
		this.setTasks(tasks);
	}

	public List<Task<T>> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task<T>> tasks) throws IllegalArgumentException {
		if (tasks == null) {
			throw new IllegalArgumentException("Tasks cannot be null.");
		}

		this.tasks = tasks;
	}

	@Override
	public void doTask(T input) throws FailedTaskException {
		try {
			for (Task<T> task : this.tasks) {
				task.doTask(input);
			}
		} catch (Exception e) {
			throw new FailedTaskException(e);
		}
	}

	@Override
	public String toString() {
		return "MultiTask [tasks=" + this.tasks + "]";
	}

}
