package com.dereekb.gae.model.crud.task.impl.delete;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.DeleteTaskConfigImpl;
import com.dereekb.gae.model.extension.taskqueue.request.ModelKeyTaskRequestBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.builder.TaskRequestSender;
import com.dereekb.gae.server.taskqueue.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestSystem;
import com.dereekb.gae.server.taskqueue.system.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditController;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * {@link DeleteTask} implementation that extends
 * {@link ModelKeyTaskRequestBuilder}.
 * <p>
 * Generally used to queue up values to {@link TaskQueueEditController}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @see {@link TaskQueueEditController}
 */
public class ScheduleDeleteTask<T extends UniqueModel> extends ModelKeyTaskRequestBuilder<T>
        implements DeleteTask<T>, TaskRequestSender<T> {

	private TaskRequestSystem system;
	private DeleteTaskConfig defaultConfig;
	private Filter<T> deleteFilter;

	public ScheduleDeleteTask(TaskRequestSystem system) {
		this(system, null);
	}

	public ScheduleDeleteTask(TaskRequestSystem system, TaskRequest request) {
		this(system, request, null, null);
	}

	public ScheduleDeleteTask(TaskRequestSystem system,
	        TaskRequest request,
	        DeleteTaskConfig defaultConfig,
	        Filter<T> deleteFilter) {
		this.setBaseRequest(request);
		this.setSystem(system);
		this.setDefaultConfig(defaultConfig);
		this.setDeleteFilter(deleteFilter);
	}

	public TaskRequestSystem getSystem() {
		return this.system;
	}

	public void setSystem(TaskRequestSystem system) {
		this.system = system;
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

	@Override
	public void setBaseRequest(TaskRequest request) {
		if (request == null) {
			request = new TaskRequestImpl("delete", Method.DELETE);
		}

		super.setBaseRequest(request);
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

		Collection<T> deletableObjects;

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

	// MARK: TaskRequestSender
	@Override
	public void sendTask(T input) throws SubmitTaskException {
		this.sendTasks(SingleItem.withValue(input));
	}

	@Override
	public void sendTasks(Iterable<T> input) throws SubmitTaskException {
		List<TaskRequest> requests = super.buildRequests(input);
		this.system.submitRequests(requests);
	}

	@Override
	public String toString() {
		return "ScheduleDeleteTask [system=" + this.system + ", defaultConfig=" + this.defaultConfig
		        + ", deleteFilter=" + this.deleteFilter + "]";
	}

}
