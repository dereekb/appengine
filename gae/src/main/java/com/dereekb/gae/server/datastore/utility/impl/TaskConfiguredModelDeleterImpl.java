package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.KeyDeleter;
import com.dereekb.gae.server.datastore.ModelDeleter;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link KeyDeleter} implementation that uses a {@link TaskRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskConfiguredModelDeleterImpl<T>
        implements ModelDeleter<T> {

	private ModelDeleter<T> deleter;
	private TaskRequestSender<T> taskRequestSender;

	public TaskConfiguredModelDeleterImpl(TaskRequestSender<T> taskRequestSender, ModelDeleter<T> deleter) {
		this.setTaskRequestSender(taskRequestSender);
		this.setDeleter(deleter);
	}

	public TaskRequestSender<T> getTaskRequestSender() {
		return this.taskRequestSender;
	}

	public void setTaskRequestSender(TaskRequestSender<T> taskRequestSender) {
		if (taskRequestSender == null) {
			throw new IllegalArgumentException("taskRequestSender cannot be null.");
		}

		this.taskRequestSender = taskRequestSender;
	}

	public ModelDeleter<T> getDeleter() {
		return this.deleter;
	}

	public void setDeleter(ModelDeleter<T> deleter) {
		if (deleter == null) {
			throw new IllegalArgumentException("deleter cannot be null.");
		}

		this.deleter = deleter;
	}

	// MARK: Deleter
	@Override
	public void delete(T entity) {
		this.deleter.delete(entity);
		this.taskRequestSender.sendTask(entity);
	}

	@Override
	public void delete(Iterable<T> entities) {
		this.deleter.delete(entities);
		this.taskRequestSender.sendTasks(entities);
	}

	@Override
	public void deleteAsync(T entity) {
		this.deleter.deleteAsync(entity);
		this.taskRequestSender.sendTask(entity);
	}

	@Override
	public void deleteAsync(Iterable<T> entities) {
		this.deleter.deleteAsync(entities);
		this.taskRequestSender.sendTasks(entities);
	}

	@Override
	public String toString() {
		return "TaskConfiguredDeleter [taskRequestSender=" + this.taskRequestSender + ", deleter=" + this.deleter + "]";
	}

}
