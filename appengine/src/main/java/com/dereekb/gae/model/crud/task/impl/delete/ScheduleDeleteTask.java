package com.dereekb.gae.model.crud.task.impl.delete;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.DeleteTaskConfigImpl;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleReviewTask;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.utilities.collections.pairs.impl.SuccessResultsPair;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

/**
 * Pre-configured {@link ScheduleReviewTask} and {@link DeleteTask} for
 * performing deletions in the taskqueue.
 * <p>
 * Used to queue up requests to {@link TaskQueueIterateController}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ScheduleDeleteTask<T extends UniqueModel> extends ScheduleReviewTask<T>
        implements DeleteTask<T> {

	private static final String DELETE_TASK = "delete";

	private DeleteTaskConfig defaultConfig;
	private Filter<T> deleteFilter;

	public ScheduleDeleteTask(String modelType, TaskScheduler scheduler) throws IllegalArgumentException {
		this(modelType, scheduler, null);
	}

	public ScheduleDeleteTask(String modelType, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(DELETE_TASK, modelType, scheduler, baseRequest);
		this.setDefaultConfig(null);
	}

	public DeleteTaskConfig getDefaultConfig() {
		return this.defaultConfig;
	}

	public void setDefaultConfig(DeleteTaskConfig defaultConfig) {
		if (defaultConfig == null) {
			defaultConfig = new DeleteTaskConfigImpl();
		}

		this.defaultConfig = defaultConfig;
	}

	public Filter<T> getDeleteFilter() {
		return this.deleteFilter;
	}

	public void setDeleteFilter(Filter<T> deleteFilter) {
		this.deleteFilter = deleteFilter;
	}

	// MARK: DeleteTask
	@Override
	public void doTask(Iterable<DeletePair<T>> input) {
		this.doTask(input, this.defaultConfig);
	}

	@Override
	public void doTask(Iterable<DeletePair<T>> input,
	                   DeleteTaskConfig configuration) {
		Collection<T> objects = DeletePair.getSources(input);

		Collection<T> deletableObjects = null;

		if (this.deleteFilter != null) {
			FilterResults<T> results = this.deleteFilter.filterObjects(objects);
			List<T> failedObjects = results.getFailingObjects();

			if (failedObjects.isEmpty() == false && configuration.isAtomic()) {
				throw new AtomicOperationException(failedObjects, AtomicOperationExceptionReason.FILTERED);
			} else {
				deletableObjects = results.getPassingObjects();
			}
		} else {
			deletableObjects = objects;
		}

		try {
			this.sendTasks(deletableObjects);
		} catch (Exception e) {
			throw new AtomicOperationException(e);
		}

		// Update results states
		SuccessResultsPair.setResultPairsSuccess(input, true);
	}

}
