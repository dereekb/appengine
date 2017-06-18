package com.dereekb.gae.server.datastore.utility.impl;

import java.util.Set;

import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.utility.StagedUpdater;
import com.dereekb.gae.server.datastore.utility.StagedUpdaterFactory;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link Updater} implementation that uses a {@link TaskRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskConfiguredUpdaterImpl<T>
        implements Updater<T>, StagedUpdaterFactory<T> {

	private Updater<T> updater;
	private TaskRequestSender<T> taskRequestSender;

	public TaskConfiguredUpdaterImpl(TaskRequestSender<T> taskRequestSender, Updater<T> updater) {
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
		this.updater.update(entity);
		this.taskRequestSender.sendTask(entity);
	}

	@Override
	public void update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.updater.update(entities);
		this.taskRequestSender.sendTasks(entities);
	}

	@Override
	public void updateAsync(T entity) throws UpdateUnkeyedEntityException {
		this.updater.updateAsync(entity);
		this.taskRequestSender.sendTask(entity);
	}

	@Override
	public void updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.updater.updateAsync(entities);
		this.taskRequestSender.sendTasks(entities);
	}

	// MARK: StagedUpdaterFactory
	@Override
	public StagedUpdater<T> makeUpdater() {
		return new StagedUpdaterImpl();
	}

	private class StagedUpdaterImpl extends AbstractStagedUpdater<T> {

		public StagedUpdaterImpl() {
			super(TaskConfiguredUpdaterImpl.this.updater);
		}

		// MARK: AbstractStagedUpdater
		@Override
		protected void finishUpdateWithEntities(Set<T> entities) {
			TaskConfiguredUpdaterImpl.this.taskRequestSender.sendTasks(entities);
		}

	}

	@Override
	public String toString() {
		return "TaskConfiguredUpdater [taskRequestSender=" + this.taskRequestSender + ", updater=" + this.updater + "]";
	}

}
