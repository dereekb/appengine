package com.dereekb.gae.utilities.task.impl;

import java.util.List;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} that performs a {@link Filter} on the input before forwarding it
 * to another {@link IterableTask}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class FilteredTask<T>
        implements IterableTask<T> {

	private Filter<T> filter;
	private IterableTask<T> task;
	private boolean atomicTask;

	public FilteredTask(Filter<T> filter, IterableTask<T> task, boolean atomicTask) {
		this.filter = filter;
		this.task = task;
		this.atomicTask = atomicTask;
	}

	public Filter<T> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<T> filter) {
		this.filter = filter;
	}

	public IterableTask<T> getTask() {
		return this.task;
	}

	public void setTask(IterableTask<T> task) {
		this.task = task;
	}

	public boolean isAtomicTask() {
		return this.atomicTask;
	}

	public void setAtomicTask(boolean atomicTask) {
		this.atomicTask = atomicTask;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		FilterResults<T> results = this.filter.filterObjects(input);
		List<T> passed = results.getPassingObjects();
		List<T> failed = results.getFailingObjects();

		if (passed.isEmpty()) {
			throw new FailedTaskException("All task items were filtered out.");
		} else {
			if (failed.isEmpty() || this.atomicTask == false) {
				this.task.doTask(passed);
			} else {
				throw new FailedTaskException("FilterTask was atomic, and atleast one item was filtered out.");
			}
		}
	}

	@Override
	public String toString() {
		return "FilteredTask [filter=" + this.filter + ", task=" + this.task + ", atomicTask=" + this.atomicTask + "]";
	}

}
