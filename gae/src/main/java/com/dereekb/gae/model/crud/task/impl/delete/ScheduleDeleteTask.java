package com.dereekb.gae.model.crud.task.impl.delete;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.DeleteTaskConfigImpl;
import com.dereekb.gae.model.extension.taskqueue.request.ModelKeyTaskRequestBuilder;
import com.dereekb.gae.model.extension.taskqueue.request.TypedModelKeyTaskRequestBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
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
public class ScheduleDeleteTask<T extends UniqueModel> extends TypedModelKeyTaskRequestBuilder<T>
        implements DeleteTask<T>, TaskRequestSender<T> {

	private TaskScheduler scheduler;
	private DeleteTaskConfig defaultConfig;
	private Filter<T> deleteFilter;

	public ScheduleDeleteTask(String modelResource, TaskScheduler scheduler) throws URISyntaxException {
		this(modelResource, scheduler, null);
	}

	public ScheduleDeleteTask(String modelResource, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws URISyntaxException {
		this.setModelResource(modelResource);

		this.setScheduler(scheduler);
		this.setBaseRequest(baseRequest);
		this.setDefaultConfig(null);
	}

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
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
		this.scheduler.schedule(requests);
	}

	@Override
	public String toString() {
		return "ScheduleDeleteTask [scheduler=" + this.scheduler + ", defaultConfig=" + this.defaultConfig
		        + ", deleteFilter=" + this.deleteFilter + "]";
	}

}
