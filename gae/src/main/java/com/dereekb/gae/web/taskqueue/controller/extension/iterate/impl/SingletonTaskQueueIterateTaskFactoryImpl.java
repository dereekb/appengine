package com.dereekb.gae.web.taskqueue.controller.extension.iterate.impl;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * {@link TaskQueueIterateTaskFactory} implementation that returns a singleton
 * {@link Task}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SingletonTaskQueueIterateTaskFactoryImpl<T extends UniqueModel>
        implements TaskQueueIterateTaskFactory<T> {

	private Task<ModelKeyListAccessor<T>> task;

	public SingletonTaskQueueIterateTaskFactoryImpl(Task<ModelKeyListAccessor<T>> task)
	        throws IllegalArgumentException {
		this.setTask(task);
	}

	public Task<ModelKeyListAccessor<T>> getTask() {
		return this.task;
	}

	public void setTask(Task<ModelKeyListAccessor<T>> task) throws IllegalArgumentException {
		if (task == null) {
			throw new IllegalArgumentException("Task cannot be null.");
		}

		this.task = task;
	}

	// MARK: TaskQueueIterateTaskFactory
	@Override
	public Task<ModelKeyListAccessor<T>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		return this.task;
	}

	@Override
	public String toString() {
		return "SingletonTaskQueueIterateTaskFactoryImpl [task=" + this.task + "]";
	}

}
