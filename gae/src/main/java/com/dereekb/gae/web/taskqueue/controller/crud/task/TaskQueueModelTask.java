package com.dereekb.gae.web.taskqueue.controller.crud.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} implementation with a {@link ModelKeyListAccessor<T>} that
 * processes tasks using {@link ModelKey} and model values.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class TaskQueueModelTask<T extends UniqueModel>
        implements Task<ModelKeyListAccessor<T>> {

	protected IterableTask<ModelKey> keyTask;
	protected IterableTask<T> modelTask;

	public TaskQueueModelTask() {}

	public TaskQueueModelTask(IterableTask<ModelKey> keyTask, IterableTask<T> modelTask) {
		this.keyTask = keyTask;
		this.modelTask = modelTask;
	}

	public IterableTask<ModelKey> getKeyTask() {
		return this.keyTask;
	}

	public void setKeyTask(IterableTask<ModelKey> keyTask) {
		this.keyTask = keyTask;
	}

	public IterableTask<T> getModelTask() {
		return this.modelTask;
	}

	public void setModelTask(IterableTask<T> modelTask) {
		this.modelTask = modelTask;
	}

	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		if (this.keyTask != null) {
			this.doKeyTask(input);
		}

		if (this.modelTask != null) {
			this.doModelTask(input);
		}
	}

	private void doKeyTask(ModelKeyListAccessor<T> input) {
		this.keyTask.doTask(input.getModelKeys());
	}

	private void doModelTask(ModelKeyListAccessor<T> input) {
		this.modelTask.doTask(input.getModels());
	}

	@Override
	public String toString() {
		return "TaskQueueModelTask [keyTask=" + this.keyTask + ", modelTask=" + this.modelTask + "]";
	}

}
