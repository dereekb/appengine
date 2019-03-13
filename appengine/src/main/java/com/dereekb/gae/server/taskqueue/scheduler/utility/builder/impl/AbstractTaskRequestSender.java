package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestModifier;

/**
 * Abstract class used to hold components used by task request senders.
 * 
 * @author dereekb
 * @see TaskRequestSenderImpl
 */
public abstract class AbstractTaskRequestSender {

	private TaskScheduler scheduler;

	private TaskRequestModifier modifier;

	protected AbstractTaskRequestSender() {}

	public AbstractTaskRequestSender(TaskScheduler scheduler) {
		this.setScheduler(scheduler);
	}

	public AbstractTaskRequestSender(TaskScheduler scheduler, TaskRequestModifier modifier) {
		this.setScheduler(scheduler);
		this.setModifier(modifier);
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

	// MARK: Internal
	protected void scheduleTasks(Collection<MutableTaskRequest> requests) {
		if (this.modifier != null) {
			requests = this.modifier.modifyRequests(requests);
		}

		this.scheduler.schedule(requests);
	}

}
