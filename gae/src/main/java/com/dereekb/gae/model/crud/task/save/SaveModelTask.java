package com.dereekb.gae.model.crud.task.save;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.task.impl.MultiTask;

/**
 * {@link Task} for saving models. Performs all tasks, then saves the input
 * using a {@link ConfiguredSetter}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SaveModelTask<T extends UniqueModel> extends MultiTask<T> {

	private ConfiguredSetter<T> setter;

	public SaveModelTask(List<Task<T>> tasks) {
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
		this.setter.save(input);
	}

	@Override
	public String toString() {
		return "SaveModelTask [setter=" + this.setter + ", tasks=" + this.tasks + "]";
	}

}
