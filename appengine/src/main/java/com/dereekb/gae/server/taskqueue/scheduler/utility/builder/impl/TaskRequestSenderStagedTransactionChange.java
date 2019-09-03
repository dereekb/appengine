package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.utility.StagedTransactionAlreadyFinishedException;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.impl.AbstractStagedTransactionChange;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link StagedTransactionChange} implementation that wraps a
 * {@link TaskRequestSender}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskRequestSenderStagedTransactionChange<T> extends AbstractStagedTransactionChange {

	private List<T> models;
	private TaskRequestSender<T> taskSender;

	public TaskRequestSenderStagedTransactionChange(TaskRequestSender<T> taskSender) {
		this(taskSender, null);
	}

	public TaskRequestSenderStagedTransactionChange(TaskRequestSender<T> taskSender, List<T> models) {
		super();
		this.setTaskSender(taskSender);
		this.setModels(models);
	}

	public List<T> getModels() {
		return this.models;
	}

	public void addModel(T model) throws StagedTransactionAlreadyFinishedException {
		this.assertIsNotComplete();
		this.models.add(model);
	}

	public void setModels(List<T> models) throws StagedTransactionAlreadyFinishedException {
		this.assertIsNotComplete();
		this.models = ListUtility.copy(models);
	}

	public TaskRequestSender<T> getTaskSender() {
		return this.taskSender;
	}

	public void setTaskSender(TaskRequestSender<T> taskSender) {
		if (taskSender == null) {
			throw new IllegalArgumentException("taskSender cannot be null.");
		}

		this.taskSender = taskSender;
	}

	// MARK: StagedTransactionChange
	@Override
	protected void finishChangesWithEntities() {
		if (this.models.isEmpty() == false) {
			this.taskSender.sendTasks(this.models);
		}
	}

	@Override
	public String toString() {
		return "TaskRequestSenderStagedTransactionChange [models=" + this.models + ", taskSender=" + this.taskSender
		        + "]";
	}

}
