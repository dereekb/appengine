package com.dereekb.gae.server.datastore.utility.impl;

import java.util.Set;

import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;
import com.dereekb.gae.server.datastore.utility.StagedStorer;
import com.dereekb.gae.server.datastore.utility.StagedStorerFactory;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link Storer} implementation that uses a {@link TaskRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskConfiguredStorerImpl<T>
        implements Storer<T>, StagedStorerFactory<T> {

	private Storer<T> storer;
	private TaskRequestSender<T> taskRequestSender;

	public TaskConfiguredStorerImpl(TaskRequestSender<T> taskRequestSender, Storer<T> storer) {
		this.setTaskRequestSender(taskRequestSender);
		this.setStorer(storer);
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

	public Storer<T> getStorer() {
		return this.storer;
	}

	public void setStorer(Storer<T> storer) {
		if (storer == null) {
			throw new IllegalArgumentException("storer cannot be null.");
		}

		this.storer = storer;
	}

	// MARK: Storer
	@Override
	public void store(T entity) throws StoreKeyedEntityException {
		this.storer.store(entity);
		this.taskRequestSender.sendTask(entity);
	}

	@Override
	public void store(Iterable<T> entities) throws StoreKeyedEntityException {
		this.storer.store(entities);
		this.taskRequestSender.sendTasks(entities);
	}

	// MARK: StagedStorerFactory
	@Override
	public StagedStorer<T> makeStorer() {
		return new StagedStorerImpl();
	}

	private class StagedStorerImpl extends AbstractStagedStorer<T> {

		public StagedStorerImpl() {
			super(TaskConfiguredStorerImpl.this.storer);
		}

		// MARK: AbstractStagedStorer
		@Override
		protected void finishUpdateWithEntities(Set<T> entities) {
			TaskConfiguredStorerImpl.this.taskRequestSender.sendTasks(entities);
		}

	}

	@Override
	public String toString() {
		return "TaskConfiguredStorer [taskRequestSender=" + this.taskRequestSender + ", storer=" + this.storer + "]";
	}

}
