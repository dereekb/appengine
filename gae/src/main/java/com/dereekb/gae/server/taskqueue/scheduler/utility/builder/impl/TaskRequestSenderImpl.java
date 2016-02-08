package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Implementation of {@link TaskRequestSender} using a
 * {@link TaskRequestBuilder} and {@link TaskScheduler}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskRequestSenderImpl<T>
        implements TaskRequestSender<T> {

	private TaskScheduler scheduler;
	private TaskRequestBuilder<T> builder;

	public TaskRequestSenderImpl() {}

	public TaskRequestSenderImpl(TaskRequestBuilder<T> builder, TaskScheduler scheduler) {
		this.builder = builder;
		this.scheduler = scheduler;
	}

	public TaskRequestBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(TaskRequestBuilder<T> builder) {
		this.builder = builder;
	}

	public TaskScheduler getSystem() {
		return this.scheduler;
	}

	public void setSystem(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void sendTask(T input) throws SubmitTaskException {
		this.sendTasks(SingleItem.withValue(input));
	}

	@Override
	public void sendTasks(Iterable<T> input) throws SubmitTaskException {
		List<TaskRequest> requests = this.builder.buildRequests(input);
		this.scheduler.schedule(requests);
	}

}
