package com.dereekb.gae.web.taskqueue.model.extension.iterate.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskExecutor;
import com.dereekb.gae.model.extension.iterate.IterateTaskExecutorFactory;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;
import com.dereekb.gae.model.extension.iterate.impl.IterateTaskExecutorFactoryImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.IterateTaskRequest;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.SequenceTaskRequest;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateControllerEntry;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.exception.InvalidIterateTaskException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.exception.UnknownIterateTaskException;

/**
 * {@link TaskQueueIterateControllerEntry} implementation.
 *
 * @author dereekb
 *
 */
public class TaskQueueIterateControllerEntryImpl<T extends ObjectifyModel<T>>
        implements TaskQueueIterateControllerEntry {

	private IterateTaskExecutorFactory<T> executorFactory;
	private ModelKeyListAccessorFactory<T> accessorFactory;
	private Map<String, TaskQueueIterateTaskFactory<T>> tasks;

	public TaskQueueIterateControllerEntryImpl() {}

	public TaskQueueIterateControllerEntryImpl(ObjectifyRegistry<T> registry,
	        Map<String, TaskQueueIterateTaskFactory<T>> tasks) throws IllegalArgumentException {
		this(new IterateTaskExecutorFactoryImpl<T>(registry), tasks, registry);
	}

	public TaskQueueIterateControllerEntryImpl(IterateTaskExecutorFactory<T> executorFactory,
	        Map<String, TaskQueueIterateTaskFactory<T>> tasks,
	        ModelKeyListAccessorFactory<T> accessorFactory) throws IllegalArgumentException {
		this.setAccessorFactory(accessorFactory);
		this.setExecutorFactory(executorFactory);
		this.setTasks(tasks);
	}

	public ModelKeyListAccessorFactory<T> getAccessorFactory() {
		return this.accessorFactory;
	}

	public void setAccessorFactory(ModelKeyListAccessorFactory<T> accessorFactory) {
		if (accessorFactory == null) {
			throw new IllegalArgumentException("accessorFactory cannot be null.");
		}

		this.accessorFactory = accessorFactory;
	}

	public IterateTaskExecutorFactory<T> getExecutorFactory() {
		return this.executorFactory;
	}

	public void setExecutorFactory(IterateTaskExecutorFactory<T> executorFactory) throws IllegalArgumentException {
		if (executorFactory == null) {
			throw new IllegalArgumentException("Executor factory cannot be null.");
		}

		this.executorFactory = executorFactory;
	}

	public Map<String, TaskQueueIterateTaskFactory<T>> getTasks() {
		return this.tasks;
	}

	public void setTasks(Map<String, TaskQueueIterateTaskFactory<T>> tasks) throws IllegalArgumentException {
		if (tasks == null || tasks.isEmpty()) {
			throw new IllegalArgumentException("Tasks map cannot be null or empty.");
		}

		this.tasks = new CaseInsensitiveMap<TaskQueueIterateTaskFactory<T>>(tasks);
	}

	// MARK: TaskQueueIterateControllerEntry
	@Override
	public void performIterateTask(IterateTaskRequest request)
	        throws UnknownIterateTaskException,
	            InvalidIterateTaskException {
		IterateTaskInput input = request.getTaskInput();
		Task<ModelKeyListAccessor<T>> task = this.getTask(input);

		try {
			IterateTaskExecutor<T> executor = this.executorFactory.makeExecutor(task);
			executor.executeTask(input);
		} catch (IterationLimitReachedException e) {
			String cursor = e.getCursor();
			request.scheduleContinuation(cursor);
		}
	}

	@Override
	public void performSequenceTask(SequenceTaskRequest request)
	        throws UnknownIterateTaskException,
	            InvalidIterateTaskException {
		IterateTaskInput input = request.getTaskInput();
		Task<ModelKeyListAccessor<T>> task = this.getTask(input);

		List<ModelKey> sequence = request.getSequence();
		ModelKeyListAccessor<T> accessor = this.accessorFactory.createAccessor(sequence);

		task.doTask(accessor);
	}

	public Task<ModelKeyListAccessor<T>> getTask(IterateTaskInput input)
	        throws UnknownIterateTaskException,
	            InvalidIterateTaskException {
		String taskName = input.getTaskName();

		TaskQueueIterateTaskFactory<T> taskFactory = this.tasks.get(taskName);
		Task<ModelKeyListAccessor<T>> task = null;

		if (taskFactory == null) {
			throw new UnknownIterateTaskException(taskName);
		} else {
			try {
				task = taskFactory.makeTask(input);
			} catch (FactoryMakeFailureException e) {
				throw new InvalidIterateTaskException(e);
			}
		}

		return task;
	}

	@Override
	public String toString() {
		return "TaskQueueIterateControllerEntryImpl [executorFactory=" + this.executorFactory + ", tasks=" + this.tasks
		        + "]";
	}

}
