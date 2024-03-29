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
import com.dereekb.gae.server.datastore.task.IterableUpdateTask;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.map.MapUtility;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

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

	public static final String REJECTED_UPDATE_CODE = "REJECTED_UPDATE";
	private static final InvalidAttributeException DEFAULT_FILTER_EXCEPTION = new InvalidAttributeException(InvalidAttribute.MODEL_ATTRIBUTE, null, "Not allowed to update this type.", REJECTED_UPDATE_CODE);

	private Filter<T> filter;

	public FilteredUpdateTaskImpl(UpdateTaskDelegate<T> delegate, IterableUpdateTask<T> saveTask, Filter<T> filter)
	        throws IllegalArgumentException {
		super(delegate, saveTask);
		this.setFilter(filter);
	}

	public FilteredUpdateTaskImpl(UpdateTaskDelegate<T> delegate,
	        IterableUpdateTask<T> saveTask,
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

		List<UpdatePair<T>> failedPairs = MapUtility.getValuesForKeys(pairsMap, failedObjects);
		UpdatePair.setPairsFailureException(failedPairs, DEFAULT_FILTER_EXCEPTION);

		if (configuration.isAtomic() && failedObjects.isEmpty() == false) {
			throw new AtomicOperationException(failedObjects, AtomicOperationExceptionReason.ATOMIC_FAILURE);
		} else if (passedObjects.isEmpty() == false) {
			List<UpdatePair<T>> passingPairs = MapUtility.getValuesForKeys(pairsMap, passedObjects);
			super.doTask(passingPairs, configuration);
		}

	}

}
