package com.dereekb.gae.web.taskqueue.model.crud.task;

import java.util.List;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.impl.MultiTask;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * {@link Task} implementation with a {@link ModelKeyListAccessor<T>} that
 * processes tasks using {@link ModelKey} and model values.
 * <p>
 * Implements {@link TaskQueueIterateTaskFactory} that returns itself by
 * default.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskQueueModelTask<T extends UniqueModel> extends MultiTask<ModelKeyListAccessor<T>>
        implements TaskQueueIterateTaskFactory<T> {

	public TaskQueueModelTask() {}

	public TaskQueueModelTask(List<Task<ModelKeyListAccessor<T>>> tasks) {
		super(tasks);
	}

	// MARK: TaskQueueIterateTaskFactory
	@Override
	public Task<ModelKeyListAccessor<T>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		return this;
	}

}
