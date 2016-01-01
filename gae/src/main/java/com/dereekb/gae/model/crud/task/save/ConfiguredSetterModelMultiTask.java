package com.dereekb.gae.model.crud.task.save;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.task.impl.MultiTask;

/**
 * {@link MultiTask} for saving or deleting models.
 * <p>
 * Performs all tasks, then saves or deletes the input using a
 * {@link ConfiguredSetter}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ConfiguredSetterModelMultiTask<T extends UniqueModel> extends MultiTask<T> {

	private ConfiguredSetter<T> setter;
	private SetterChangeState state = SetterChangeState.SAVE;

	public ConfiguredSetterModelMultiTask(List<Task<T>> tasks, ConfiguredSetter<T> setter) {
		super(tasks);
		this.setter = setter;
	}

	public ConfiguredSetterModelMultiTask(List<Task<T>> tasks, ConfiguredSetter<T> setter, SetterChangeState state) {
		super(tasks);
		this.setter = setter;
		this.state = state;
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

	public SetterChangeState getState() {
		return this.state;
	}

	public void setState(SetterChangeState state) throws IllegalArgumentException {
		if (state == null) {
			throw new IllegalArgumentException("State cannot be set null.");
		}

		this.state = state;
	}

	// MARK: Task
	@Override
	public void doTask(T input) throws FailedTaskException {
		super.doTask(input);

		switch (this.state) {
			case SAVE:
				this.setter.save(input);
				break;
			case DELETE:
				this.setter.delete(input);
				break;
		}
	}

	@Override
    public String toString() {
		return "ConfiguredSetterModelMultiTask [setter=" + this.setter + ", tasks=" + this.tasks + "]";
    }

}
