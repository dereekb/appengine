package com.dereekb.gae.web.taskqueue.controller.crud.task;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.utility.ConfiguredDeleter;
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

	private ConfiguredDeleter deleter;

	public TaskQueueDeleteModelTask(ConfiguredDeleter deleter) {
		this(deleter, null);
	}

	public TaskQueueDeleteModelTask(ConfiguredDeleter deleter, List<Task<ModelKeyListAccessor<T>>> tasks) {
		super(tasks);
		this.setDeleter(deleter);
	}

	public ConfiguredDeleter getDeleter() {
		return this.deleter;
	}

	public void setDeleter(ConfiguredDeleter deleter) {
		this.deleter = deleter;
	}

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
