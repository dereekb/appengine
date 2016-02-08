package com.dereekb.gae.web.taskqueue.controller.extension.iterate.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskExecutor;
import com.dereekb.gae.model.extension.iterate.IterateTaskExecutorFactory;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.IterateTaskRequest;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.TaskQueueIterateControllerEntry;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.exception.UnknownIterateTaskException;

/**
 * {@link TaskQueueIterateControllerEntry} implementation.
 *
 * @author dereekb
 *
 */
public class TaskQueueIterateControllerEntryImpl<T extends UniqueModel>
        implements TaskQueueIterateControllerEntry {

	private IterateTaskExecutorFactory<T> executorFactory;
	private Map<String, Task<ModelKeyListAccessor<T>>> tasks;

	public TaskQueueIterateControllerEntryImpl() {}

	public TaskQueueIterateControllerEntryImpl(Map<String, Task<ModelKeyListAccessor<T>>> tasks) {
		this.tasks = tasks;
	}

	public IterateTaskExecutorFactory<T> getExecutorFactory() {
		return this.executorFactory;
	}

	public void setExecutorFactory(IterateTaskExecutorFactory<T> executorFactory) {
		this.executorFactory = executorFactory;
	}

	public Map<String, Task<ModelKeyListAccessor<T>>> getTasks() {
		return this.tasks;
	}

	public void setTasks(Map<String, Task<ModelKeyListAccessor<T>>> tasks) {
		this.tasks = tasks;
	}

	// MARK: TaskQueueIterateControllerEntry
	@Override
	public void performTask(IterateTaskRequest request) throws UnknownIterateTaskException {
		IterateTaskInput input = request.getTaskInput();
		String taskName = input.getTaskName();
		Task<ModelKeyListAccessor<T>> task = this.getTask(taskName);

		try {
			IterateTaskExecutor<T> executor = this.executorFactory.makeExecutor(task);
			executor.executeTask(input);
		} catch (IterationLimitReachedException e) {
			String cursor = e.getCursor();
			request.scheduleContinuation(cursor);
		}
	}

	public Task<ModelKeyListAccessor<T>> getTask(String taskName) throws UnknownIterateTaskException {
		Task<ModelKeyListAccessor<T>> task = this.tasks.get(taskName);

		if (task == null) {
			throw new UnknownIterateTaskException();
		}

		return task;
	}

}
