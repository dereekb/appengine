package com.dereekb.gae.utilities.task.iteration;

import java.util.Iterator;

import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.iteration.exception.IncompleteTaskIterationException;

/**
 * {@link Task} that iterates over the input. Generally used for performing
 * tasks on multiple single elements. The entire {@link Iterator} input must be
 * used.
 *
 * @author dereekb
 */
public interface IterationTask<T>
        extends Task<Iterator<T>> {

	/**
	 * Performs the task, using all input on the {@link Iterator}.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 * @throws IncompleteTaskIterationException
	 *             if {@code input} was not fully used. That is, if
	 *             {@link Iterator#hasNext()} returns {@code true} after the
	 *             task has been completed.
	 */
	@Override
	public void doTask(Iterator<T> input) throws IncompleteTaskIterationException;

}
