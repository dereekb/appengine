package com.dereekb.gae.model.crud.extension.delete.task;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.task.impl.MultiTask;

/**
 * {@link Task} for deleting models. Performs all tasks, then deletes the input
 * using a {@link ConfiguredSetter}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class DeleteModelTask<T extends UniqueModel> extends MultiTask<T> {

	private ConfiguredSetter<T> setter;

	public DeleteModelTask(List<Task<T>> tasks) {
		super(tasks);
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

	// MARK: Task
	@Override
	public void doTask(T input) throws FailedTaskException {
		super.doTask(input);
		this.setter.delete(input);
	}

	@Override
    public String toString() {
		return "DeleteModelTask [setter=" + this.setter + ", tasks=" + this.tasks + "]";
    }

}
