package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link ConfiguredSetterImpl} extension that sends a task for updated
 * entities.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @deprecated replaced with {@link TaskConfiguredUpdaterImpl} or
 *             {@link TaskConfiguredDeleterImpl}.
 */
@Deprecated
public class TaskConfiguredSetterImpl<T> extends ConfiguredSetterImpl<T> {

	private TaskRequestSender<T> taskRequestSender;

	public TaskConfiguredSetterImpl(TaskRequestSender<T> taskRequestSender, Setter<T> setter, boolean asynchronous)
	        throws IllegalArgumentException {
		super(setter, asynchronous);
		this.setTaskRequestSender(taskRequestSender);
	}

	public TaskConfiguredSetterImpl(TaskRequestSender<T> taskRequestSender, Setter<T> setter)
	        throws IllegalArgumentException {
		super(setter);
		this.setTaskRequestSender(taskRequestSender);
	}

	public TaskRequestSender<T> getTaskRequestSender() {
		return this.taskRequestSender;
	}

	public void setTaskRequestSender(TaskRequestSender<T> taskRequestSender) throws IllegalArgumentException {
		if (taskRequestSender == null) {
			throw new IllegalArgumentException();
		}

		this.taskRequestSender = taskRequestSender;
	}

	// MARK: Setter
	@Override
	public void update(T entity) throws UpdateUnkeyedEntityException {
		super.update(entity);
		this.taskRequestSender.sendTask(entity);
	}

	@Override
	public void update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		super.update(entities);
		this.taskRequestSender.sendTasks(entities);
	}

	@Override
	public String toString() {
		return "TaskConfiguredSetterImpl [taskRequestSender=" + this.taskRequestSender + "]";
	}

}
