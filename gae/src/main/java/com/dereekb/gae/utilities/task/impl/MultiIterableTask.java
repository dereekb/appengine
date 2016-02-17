package com.dereekb.gae.utilities.task.impl;

import java.util.List;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;

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

	public MultiIterableTask() {
		super();
	}

	public MultiIterableTask(List<Task<Iterable<T>>> tasks) {
		super(tasks);
	}

}
