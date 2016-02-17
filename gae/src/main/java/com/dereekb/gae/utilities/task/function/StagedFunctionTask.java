package com.dereekb.gae.utilities.task.function;

import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;

/**
 * Generic {@link StagedFunction} that uses a {@link Task} to perform the
 * function.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <W>
 *            function objects
 */
public class StagedFunctionTask<T, W extends StagedFunctionObject<T>> extends FilteredStagedFunction<T, W> {

	private IterableTask<W> task;

	public StagedFunctionTask(IterableTask<W> task) {
		this.task = task;
	}

	public IterableTask<W> getTask() {
		return this.task;
	}

	public void setTask(IterableTask<W> task) {
		this.task = task;
	}

	// MARK: Staged Function
	@Override
	protected void doFunction() {
		Iterable<W> functionObjects = this.getWorkingObjects();
		this.task.doTask(functionObjects);
	}

}