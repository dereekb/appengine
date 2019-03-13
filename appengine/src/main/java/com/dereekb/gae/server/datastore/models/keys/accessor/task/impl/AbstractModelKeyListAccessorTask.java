package com.dereekb.gae.server.datastore.models.keys.accessor.task.impl;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.ModelKeyListAccessorTask;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * Abstract {@link ModelKeyListAccessorTask} implementation that also implements
 * {@link TaskQueueIterateTaskFactory} to return itself.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelKeyListAccessorTask<T extends UniqueModel>
        implements ModelKeyListAccessorTask<T>, TaskQueueIterateTaskFactory<T> {

	// MARK: TaskQueueIterateTaskFactory
	@Override
	public Task<ModelKeyListAccessor<T>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		return this;
	}

}
