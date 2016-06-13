package com.dereekb.gae.model.crud.task.impl.task;

import java.net.URISyntaxException;
import java.util.List;

import com.dereekb.gae.model.extension.taskqueue.request.TypedModelKeyTaskRequestBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.SingleItem;


public abstract class AbstractScheduleReviewTask<T extends UniqueModel> extends TypedModelKeyTaskRequestBuilder<T>
        implements TaskRequestSender<T> {

	protected TaskScheduler scheduler;

	public AbstractScheduleReviewTask(String modelResource, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws URISyntaxException, IllegalArgumentException {
		this.setModelResource(modelResource);
		this.setScheduler(scheduler);
		this.setBaseRequest(baseRequest);
	}

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	// MARK: TypedModelKeyTaskRequestBuilder
	@Override
	public void setBaseRequest(TaskRequest request) throws IllegalArgumentException {
		if (request == null) {
			throw new IllegalArgumentException("Base task request cannot be null.");
		}

		super.setBaseRequest(request);
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
		return "AbstractScheduleReviewTask [scheduler=" + this.scheduler + "]";
	}

}
