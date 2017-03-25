package com.dereekb.gae.server.datastore.task.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.task.IterableStoreTask;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.task.impl.MultiIterableTask;

/**
 * {@link MultiIterableTask} extension that also implements
 * {@link IterableStoreTask}. Performs the tasks, then calls the store task.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IterableStoreMultiTaskImpl<T extends UniqueModel> extends MultiIterableTask<T>
        implements IterableStoreTask<T>, IterableTask<T> {

	private IterableStoreTask<T> storeTask;

	public IterableStoreMultiTaskImpl(Task<Iterable<T>> task, IterableStoreTask<T> storeTask) {
		super(task);
		this.setStoreTask(storeTask);
	}

	public IterableStoreMultiTaskImpl(List<Task<Iterable<T>>> tasks, IterableStoreTask<T> storeTask) {
		super(tasks);
		this.setStoreTask(storeTask);
	}

	public IterableStoreTask<T> getStoreTask() {
		return this.storeTask;
	}

	public void setStoreTask(IterableStoreTask<T> storeTask) {
		if (storeTask == null) {
			throw new IllegalArgumentException("storeTask cannot be null.");
		}

		this.storeTask = storeTask;
	}

	// MARK: MultiTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		this.doStoreTask(input);
	}

	// MARK: IterableStoreTask
	@Override
	public void doStoreTask(Iterable<T> input) throws FailedTaskException {
		super.doTask(input);
		this.storeTask.doStoreTask(input);
	}

	@Override
	public String toString() {
		return "IterableStoreMultiTaskImpl [storeTask=" + this.storeTask + "]";
	}

}
