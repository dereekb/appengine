package com.dereekb.gae.model.crud.task.save;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.task.impl.MultiIterableTask;
import com.dereekb.gae.utilities.task.impl.MultiTask;

/**
 * {@link MultiTask} for storing, updating or deleting models.
 * <p>
 * Performs all tasks, then stores, updates or deletes the input using a
 * {@link ConfiguredSetter}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ConfiguredSetterModelMultiIterableTask<T extends UniqueModel> extends MultiIterableTask<T>
        implements IterableSetterTask<T> {

	private ConfiguredSetterModelsTask<T> setterTask;

	public ConfiguredSetterModelMultiIterableTask(Task<Iterable<T>> task, ConfiguredSetter<T> setter) {
		this(task, new ConfiguredSetterModelsTask<T>(setter));
	}

	public ConfiguredSetterModelMultiIterableTask(List<Task<Iterable<T>>> tasks, ConfiguredSetter<T> setter) {
		this(tasks, setter, null);
	}

	public ConfiguredSetterModelMultiIterableTask(Task<Iterable<T>> task,
	        ConfiguredSetter<T> setter,
	        SetterChangeState state) {
		this(task, new ConfiguredSetterModelsTask<T>(setter, state));
	}

	public ConfiguredSetterModelMultiIterableTask(List<Task<Iterable<T>>> tasks,
	        ConfiguredSetter<T> setter,
	        SetterChangeState state) {
		this(tasks, new ConfiguredSetterModelsTask<T>(setter, state));
	}

	public ConfiguredSetterModelMultiIterableTask(Task<Iterable<T>> task, ConfiguredSetterModelsTask<T> setterTask) {
		super(task);
		this.setSetterTask(setterTask);
	}

	public ConfiguredSetterModelMultiIterableTask(List<Task<Iterable<T>>> tasks,
	        ConfiguredSetterModelsTask<T> setterTask) {
		super(tasks);
		this.setSetterTask(setterTask);
	}

	public ConfiguredSetterModelsTask<T> getSetterTask() {
		return this.setterTask;
	}

	public void setSetterTask(ConfiguredSetterModelsTask<T> setterTask) throws IllegalArgumentException {
		if (setterTask == null) {
			throw new IllegalArgumentException("setterTask cannot be null.");
		}

		this.setterTask = setterTask;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		super.doTask(input);
		this.setterTask.doTask(input);
	}

	@Deprecated
	@Override
	public void doTask(Iterable<T> input,
	                   boolean async)
	        throws FailedTaskException {
		super.doTask(input);
		this.setterTask.doTask(input, async);
	}

	// MARK: Saver Task
	@Override
	public void doStoreTask(Iterable<T> input) throws FailedTaskException {
		super.doTask(input);
		this.setterTask.doStoreTask(input);
	}

	@Override
	public void doUpdateTask(Iterable<T> input) throws FailedTaskException {
		super.doTask(input);
		this.setterTask.doUpdateTask(input);
	}

	@Override
	public void doUpdateTask(Iterable<T> input,
	                         boolean async)
	        throws FailedTaskException {
		super.doTask(input);
		this.setterTask.doUpdateTask(input, async);
	}

	// MARK: Deleter Task
	@Override
	public void doDeleteTask(Iterable<T> input) throws FailedTaskException {
		super.doTask(input);
		this.setterTask.doDeleteTask(input);
	}

	@Override
	public void doDeleteTask(Iterable<T> input,
	                         boolean async)
	        throws FailedTaskException {
		super.doTask(input);
		this.setterTask.doDeleteTask(input, async);
	}

	@Override
	public String toString() {
		return "ConfiguredSetterModelMultiIterableTask [setterTask=" + this.setterTask + ", getTasks()="
		        + this.getTasks() + "]";
	}

}
