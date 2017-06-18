package com.dereekb.gae.server.datastore.models.keys.accessor.task;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.LoadedModelKeyListAccessor;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} implementation that filters the models from the task input, then
 * forwards them to another task if not empty or if configured to send anyways.
 * 
 * @author dereekb
 *
 */
public class ModelKeyListAccessorTaskFilter<T extends UniqueModel>
        implements Task<ModelKeyListAccessor<T>> {

	public static final boolean DEFAULT_EXECUTE_IF_EMPTY = false;

	private Filter<T> filter;
	private Task<ModelKeyListAccessor<T>> task;

	private boolean executeIfEmpty;

	public ModelKeyListAccessorTaskFilter(Filter<T> filter, Task<ModelKeyListAccessor<T>> task) {
		this(filter, task, DEFAULT_EXECUTE_IF_EMPTY);
	}

	public ModelKeyListAccessorTaskFilter(Filter<T> filter,
	        Task<ModelKeyListAccessor<T>> task,
	        boolean executeIfEmpty) {
		this.setFilter(filter);
		this.setTask(task);
		this.setExecuteIfEmpty(executeIfEmpty);
	}

	public Filter<T> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<T> filter) {
		if (filter == null) {
			throw new IllegalArgumentException("filter cannot be null.");
		}

		this.filter = filter;
	}

	public Task<ModelKeyListAccessor<T>> getTask() {
		return this.task;
	}

	public void setTask(Task<ModelKeyListAccessor<T>> task) {
		if (task == null) {
			throw new IllegalArgumentException("task cannot be null.");
		}

		this.task = task;
	}

	public boolean isExecuteIfEmpty() {
		return this.executeIfEmpty;
	}

	public void setExecuteIfEmpty(boolean executeIfEmpty) {
		this.executeIfEmpty = executeIfEmpty;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		FilterResults<T> results = this.filter.filterObjects(input.getModels());

		List<T> passedObjects = results.getPassingObjects();

		if (this.executeIfEmpty || !passedObjects.isEmpty()) {
			ModelKeyListAccessor<T> filteredAccessor = new LoadedModelKeyListAccessor<T>(input.getModelType(),
			        passedObjects);
			this.task.doTask(filteredAccessor);
		}
	}

	@Override
	public String toString() {
		return "ModelKeyListAccessorTaskFilter [filter=" + this.filter + ", task=" + this.task + ", executeIfEmpty="
		        + this.executeIfEmpty + "]";
	}

}
