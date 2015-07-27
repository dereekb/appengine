package com.dereekb.gae.web.taskqueue.controller.extension.iterate.impl;

import java.util.Map;

import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.IterateTaskInput;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.TaskQueueIterateControllerEntry;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.exception.UnknownIterateTaskException;

/**
 * {@link TaskQueueIterateControllerEntry} implementation.
 *
 * @author dereekb
 *
 */
public class TaskQueueIterateControllerEntryImpl
        implements TaskQueueIterateControllerEntry {

	private Map<String, Task<IterateTaskInput>> tasks;

	public TaskQueueIterateControllerEntryImpl() {}

	public TaskQueueIterateControllerEntryImpl(Map<String, Task<IterateTaskInput>> tasks) {
		this.tasks = tasks;
	}

	public Map<String, Task<IterateTaskInput>> getTasks() {
		return this.tasks;
	}

	public void setTasks(Map<String, Task<IterateTaskInput>> tasks) {
		this.tasks = tasks;
	}

	// MARK: TaskQueueIterateControllerEntry
	@Override
	public void performTask(IterateTaskInput input) throws UnknownIterateTaskException {
		String taskName = input.getTaskName();

		Task<IterateTaskInput> task = this.tasks.get(taskName);

		if (task == null) {
			throw new UnknownIterateTaskException();
		} else {
			task.doTask(input);
		}
	}

}
