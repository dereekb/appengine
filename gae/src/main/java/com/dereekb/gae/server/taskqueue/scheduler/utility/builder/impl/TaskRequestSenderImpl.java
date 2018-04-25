package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestModifier;
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
public class TaskRequestSenderImpl<T> extends AbstractTaskRequestSender
        implements TaskRequestSender<T> {

	private TaskRequestBuilder<T> builder;

	protected TaskRequestSenderImpl() {}

	public TaskRequestSenderImpl(TaskRequestBuilder<T> builder, TaskScheduler scheduler) {
		super(scheduler);
		this.setBuilder(builder);
	}

	public TaskRequestSenderImpl(TaskScheduler scheduler, TaskRequestModifier modifier, TaskRequestBuilder<T> builder) {
		super(scheduler, modifier);
		this.setBuilder(builder);
	}

	public TaskRequestBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(TaskRequestBuilder<T> builder) throws IllegalArgumentException {
		if (builder == null) {
			throw new IllegalArgumentException("Builder cannot be null");
		}

		this.builder = builder;
	}

	// MARK: TaskRequestSender
	@Override
	public void sendTask(T input) throws SubmitTaskException {
		this.sendTasks(SingleItem.withValue(input));
	}

	@Override
	public void sendTasks(Iterable<T> input) throws SubmitTaskException {
		Collection<MutableTaskRequest> requests = this.builder.buildRequests(input);
		this.scheduleTasks(requests);
	}

	@Override
	public String toString() {
		return "TaskRequestSenderImpl [builder=" + this.builder + "]";
	}

}
