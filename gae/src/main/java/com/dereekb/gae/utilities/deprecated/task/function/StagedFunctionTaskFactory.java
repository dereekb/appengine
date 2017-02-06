package com.dereekb.gae.utilities.task.function;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.AbstractFilteredStagedFunctionFactory;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * Factory used for building {@link StagedFunctionTask} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class StagedFunctionTaskFactory<T, W extends StagedFunctionObject<T>> extends AbstractFilteredStagedFunctionFactory<StagedFunctionTask<T, W>, T, W> {

	private IterableTask<W> task;

	public IterableTask<W> getTask() {
		return this.task;
	}

	public void setTask(IterableTask<W> task) {
		this.task = task;
	}

	@Override
	protected StagedFunctionTask<T, W> newStagedFunction() {
		return new StagedFunctionTask<T, W>(this.task);
	}

}