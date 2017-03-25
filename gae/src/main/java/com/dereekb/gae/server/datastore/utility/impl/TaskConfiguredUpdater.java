package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link Updater} implementation that uses a {@link TaskRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskConfiguredUpdater<T>
        implements Updater<T> {

	private Updater<T> updater;
	private TaskRequestSender<T> taskRequestSender;

	public TaskConfiguredUpdater(TaskRequestSender<T> taskRequestSender, Updater<T> updater) {
		this.setTaskRequestSender(taskRequestSender);
		this.setUpdater(updater);
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

	public Updater<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}

		this.updater = updater;
	}

	// MARK: Updater
	@Override
	public void update(T entity) throws UpdateUnkeyedEntityException {
		this.update(entity, null);
	}

	@Override
	public void update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.update(entities, null);
	}

	@Override
	public void update(T entity,
	                   Boolean async)
	        throws UpdateUnkeyedEntityException {
		this.updater.update(entity, async);
		this.taskRequestSender.sendTask(entity);
	}

	@Override
	public void update(Iterable<T> entities,
	                   Boolean async)
	        throws UpdateUnkeyedEntityException {
		this.updater.update(entities, async);
		this.taskRequestSender.sendTasks(entities);
	}

	@Override
	public String toString() {
		return "TaskConfiguredUpdater [taskRequestSender=" + this.taskRequestSender + ", updater=" + this.updater + "]";
	}

}
