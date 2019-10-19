package com.dereekb.gae.model.crud.task.impl.filtered;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link DeleteTask} that wraps another {@link DeleteTask} and contains a
 * {@link Filter}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class FilteredScheduleDeleteTask<T extends UniqueModel>
        implements DeleteTask<T> {

	private DeleteTask<T> deleteTask;
	private Filter<T> deleteFilter;

	public FilteredScheduleDeleteTask(DeleteTask<T> deleteTask, Filter<T> deleteFilter) {
		super();
		this.setDeleteTask(deleteTask);
		this.setDeleteFilter(deleteFilter);
	}

	public DeleteTask<T> getDeleteTask() {
		return this.deleteTask;
	}

	public void setDeleteTask(DeleteTask<T> deleteTask) {
		if (deleteTask == null) {
			throw new IllegalArgumentException("deleteTask cannot be null.");
		}

		this.deleteTask = deleteTask;
	}

	public Filter<T> getDeleteFilter() {
		return this.deleteFilter;
	}

	public void setDeleteFilter(Filter<T> deleteFilter) {
		if (deleteFilter == null) {
			throw new IllegalArgumentException("deleteFilter cannot be null.");
		}

		this.deleteFilter = deleteFilter;
	}

	// MARK: DeleteTask
	@Override
	public void doTask(Iterable<DeletePair<T>> input) throws FailedTaskException {
		this.doTask(input, null);
	}

	@Override
	public void doTask(Iterable<DeletePair<T>> input,
	                   DeleteTaskConfig configuration)
	        throws FailedTaskException {

		boolean forceDelete = configuration != null && configuration.isForceDelete();

		Collection<T> objects = DeletePair.getSources(input);
		Iterable<DeletePair<T>> deletablePairs = input;

		if (!forceDelete) {
			FilterResults<T> results = this.deleteFilter.filterObjects(objects);
			List<T> failedObjects = results.getFailingObjects();

			if (failedObjects.isEmpty() == false && configuration.isAtomic()) {
				throw new AtomicOperationException(failedObjects, AtomicOperationExceptionReason.FILTERED);
			} else {
				Collection<T> deletableObjects = results.getPassingObjects();

				Map<T, DeletePair<T>> inputMap = DeletePair.pairsKeyMap(input);
				List<DeletePair<T>> filteredDeletablePairs = new ArrayList<DeletePair<T>>();

				for (T deletableObject : deletableObjects) {
					DeletePair<T> pair = inputMap.get(deletableObject);
					filteredDeletablePairs.add(pair);
				}

				deletablePairs = filteredDeletablePairs;

				// Set Failed On Filtered
				for (T failedObject : failedObjects) {
					DeletePair<T> pair = inputMap.get(failedObject);
					pair.setSuccessful(false);
				}
			}
		}

		this.deleteTask.doTask(deletablePairs, configuration);
	}

	@Override
	public String toString() {
		return "FilteredScheduleDeleteTask [deleteTask=" + this.deleteTask + ", deleteFilter=" + this.deleteFilter
		        + "]";
	}

}
