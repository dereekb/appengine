package com.dereekb.gae.model.crud.task.impl.filtered;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;
import com.dereekb.gae.model.crud.task.impl.UpdateTaskImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.map.MapUtility;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * {@link UpdateTaskImpl} extension that adds a filter to filter objects from
 * getting updated.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class FilteredUpdateTaskImpl<T extends UniqueModel> extends UpdateTaskImpl<T> {

	private Filter<T> filter;

	public FilteredUpdateTaskImpl(UpdateTaskDelegate<T> delegate, IterableTask<T> saveTask, Filter<T> filter)
	        throws IllegalArgumentException {
		super(delegate, saveTask);
		this.setFilter(filter);
	}

	public FilteredUpdateTaskImpl(UpdateTaskDelegate<T> delegate,
	        IterableTask<T> saveTask,
	        TaskRequestSender<T> sender,
	        Filter<T> filter) throws IllegalArgumentException {
		super(delegate, saveTask, sender);
		this.setFilter(filter);
	}

	public Filter<T> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<T> filter) throws IllegalArgumentException {
		if (filter == null) {
			throw new IllegalArgumentException("Filter cannot be null.");
		}

		this.filter = filter;
	}

	// MARK: Override
	@Override
	public void doTask(Iterable<UpdatePair<T>> input,
	                   UpdateTaskConfig configuration) {
		Map<T, UpdatePair<T>> pairsMap = UpdatePair.getObjectsMap(input);

		Set<T> models = pairsMap.keySet();
		FilterResults<T> filterResults = this.filter.filterObjects(models);

		List<T> passedObjects = filterResults.getPassingObjects();
		List<T> failedObjects = filterResults.getFailingObjects();

		if (configuration.isAtomic()) {
			throw new AtomicOperationException(failedObjects, AtomicOperationExceptionReason.UNAVAILABLE);
		} else if (passedObjects.isEmpty() == false) {
			List<UpdatePair<T>> passingPairs = MapUtility.getValuesForKeys(pairsMap, passedObjects);
			super.doTask(passingPairs, configuration);
		}

		List<UpdatePair<T>> failedPairs = MapUtility.getValuesForKeys(pairsMap, failedObjects);
		UpdatePair.setResultPairsSuccess(failedPairs, false);
	}

}
