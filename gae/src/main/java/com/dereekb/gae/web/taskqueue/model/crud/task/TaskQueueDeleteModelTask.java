package com.dereekb.gae.web.taskqueue.model.crud.task;

import java.util.List;

import com.dereekb.gae.server.datastore.KeyDeleter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link TaskQueueModelTask} that also deletes the models when complete.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskQueueDeleteModelTask<T extends UniqueModel> extends TaskQueueModelTask<T> {

	private KeyDeleter deleter;

	public TaskQueueDeleteModelTask(KeyDeleter deleter) {
		this(deleter, null);
	}

	public TaskQueueDeleteModelTask(KeyDeleter deleter, List<Task<ModelKeyListAccessor<T>>> tasks) {
		super(tasks);
		this.setDeleter(deleter);
	}

	public KeyDeleter getDeleter() {
		return this.deleter;
	}

	public void setDeleter(KeyDeleter deleter) {
		this.deleter = deleter;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		super.doTask(input);
		this.deleter.deleteWithKeys(input.getModelKeys());
	}

	@Override
	public String toString() {
		return "TaskQueueDeleteModelTask [deleter=" + this.deleter + ", tasks=" + this.getTasks() + "]";
	}

}
