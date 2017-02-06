package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.task.impl.MultiIterableTask;

/**
 * {@link IterableTask} for saving or deleting models.
 * <p>
 * Is generally used in conjunction with a {@link MultiIterableTask} or similar
 * type to save after other tasks have been completed on the input.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ConfiguredSetterModelsTask<T extends UniqueModel>
        implements IterableSetterTask<T> {

	private ConfiguredSetter<T> setter;
	private SetterChangeState state;

	public ConfiguredSetterModelsTask(ConfiguredSetter<T> setter) {
		this(setter, SetterChangeState.SAVE);
	}

	public ConfiguredSetterModelsTask(ConfiguredSetter<T> setter, SetterChangeState state) {
		this.setSetter(setter);
		this.setState(state);
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) throws IllegalArgumentException {
		if (setter == null) {
			throw new IllegalArgumentException("Setter cannot be null.");
		}

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
	public void doTask(Iterable<T> input) throws FailedTaskException {
		switch (this.state) {
			case SAVE:
				this.doSaveTask(input);
				break;
			case DELETE:
				this.setter.delete(input);
				break;
		}
	}

	@Override
	public void doTask(Iterable<T> input,
	                   boolean async)
	        throws FailedTaskException {
		switch (this.state) {
			case SAVE:
				this.doSaveTask(input, async);
				break;
			case DELETE:
				this.setter.delete(input, async);
				break;
		}
	}

	// MARK: IterableSaveTask
	@Override
	public void doSaveTask(Iterable<T> input) throws FailedTaskException {
		this.setter.save(input);
	}

	@Override
	public void doSaveTask(Iterable<T> input,
	                       boolean async)
	        throws FailedTaskException {
		this.setter.save(input, async);
	}

	@Override
	public String toString() {
		return "SaveModelMultiTask [setter=" + this.setter + "]";
	}

}
