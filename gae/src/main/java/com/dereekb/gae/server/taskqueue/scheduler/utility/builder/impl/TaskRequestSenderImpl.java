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
public class TaskRequestSenderImpl<T>
        implements TaskRequestSender<T> {

	private TaskScheduler scheduler;
	private TaskRequestBuilder<T> builder;

	private TaskRequestModifier modifier;

	protected TaskRequestSenderImpl() {}

	public TaskRequestSenderImpl(TaskRequestBuilder<T> builder, TaskScheduler scheduler) {
		this.setBuilder(builder);
		this.setScheduler(scheduler);
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

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) throws IllegalArgumentException {
		if (scheduler == null) {
			throw new IllegalArgumentException("Scheduler cannot be null");
		}

		this.scheduler = scheduler;
	}

	public TaskRequestModifier getModifier() {
		return this.modifier;
	}

	public void setModifier(TaskRequestModifier modifier) {
		this.modifier = modifier;
	}

	// MARK: TaskRequestSender
	@Override
	public void sendTask(T input) throws SubmitTaskException {
		this.sendTasks(SingleItem.withValue(input));
	}

	@Override
	public void sendTasks(Iterable<T> input) throws SubmitTaskException {
		Collection<MutableTaskRequest> requests = this.builder.buildRequests(input);

		if (this.modifier != null) {
			requests = this.modifier.modifyRequests(requests);
		}

		this.scheduler.schedule(requests);
	}

	@Override
	public String toString() {
		return "TaskRequestSenderImpl [scheduler=" + this.scheduler + ", builder=" + this.builder + "]";
	}

}
