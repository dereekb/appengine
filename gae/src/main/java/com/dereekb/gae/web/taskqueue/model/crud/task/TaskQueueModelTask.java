package com.dereekb.gae.web.taskqueue.model.crud.task;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.impl.MultiTask;

/**
 * {@link Task} implementation with a {@link ModelKeyListAccessor<T>} that
 * processes tasks using {@link ModelKey} and model values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskQueueModelTask<T extends UniqueModel> extends MultiTask<ModelKeyListAccessor<T>> {

	public TaskQueueModelTask() {}

	public TaskQueueModelTask(List<Task<ModelKeyListAccessor<T>>> tasks) {
		super(tasks);
	}

}
