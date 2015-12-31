package com.dereekb.gae.web.taskqueue.controller.crud.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.utility.ConfiguredDeleter;
import com.dereekb.gae.utilities.task.IterableTask;
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
	    super();
		this.deleter = deleter;
    }

	public TaskQueueDeleteModelTask(IterableTask<ModelKey> keyTask, IterableTask<T> modelTask, ConfiguredDeleter deleter) {
	    super(keyTask, modelTask);
		this.deleter = deleter;
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
		return "TaskQueueDeleteModelTask [deleter=" + this.deleter + ", keyTask=" + this.keyTask + ", modelTask="
		        + this.modelTask + "]";
	}

}
