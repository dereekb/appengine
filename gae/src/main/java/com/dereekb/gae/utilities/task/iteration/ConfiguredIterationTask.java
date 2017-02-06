package com.dereekb.gae.utilities.task.iteration;

import java.util.Iterator;

import com.dereekb.gae.utilities.task.ConfigurableTask;
import com.dereekb.gae.utilities.task.iteration.exception.IncompleteTaskIterationException;

/**
 * {@link ConfiguredTask} that also extends the {@link IterationTask} interface.
 *
 * @author dereekb
 */
public interface ConfiguredIterationTask<T, C>
        extends IterationTask<T>, ConfigurableTask<Iterator<T>, C> {

	/**
	 * Performs the task, configured with {@code configuration} and using all
	 * values in the input {@link Iterator}.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 * @param configuration
	 *            Configuration value. Can be {@code null}.
	 * @throws IncompleteTaskIterationException
	 *             if {@code input} was not fully used. That is, if
	 *             {@link Iterator#hasNext()} returns {@code true} after the
	 *             task has been completed.
	 */
	@Override
	public void doTask(Iterator<T> input,
	                   C configuration) throws IncompleteTaskIterationException;

}
