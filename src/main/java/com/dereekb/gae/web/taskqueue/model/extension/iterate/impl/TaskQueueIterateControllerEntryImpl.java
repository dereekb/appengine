package com.dereekb.gae.web.taskqueue.model.extension.iterate.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskExecutor;
import com.dereekb.gae.model.extension.iterate.IterateTaskExecutorFactory;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;
import com.dereekb.gae.model.extension.iterate.impl.IterateTaskExecutorFactoryImpl;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.IterateTaskRequest;
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
public class TaskQueueIterateControllerEntryImpl<T extends ObjectifyModel<T>> extends TaskQueueSequenceControllerEntryImpl<T>
        implements TaskQueueIterateControllerEntry {

	private IterateTaskExecutorFactory<T> executorFactory;

	public TaskQueueIterateControllerEntryImpl() {}

	public TaskQueueIterateControllerEntryImpl(ObjectifyRegistry<T> registry,
	        Map<String, TaskQueueIterateTaskFactory<T>> tasks) throws IllegalArgumentException {
		this(new IterateTaskExecutorFactoryImpl<T>(registry), tasks, registry);
	}

	public TaskQueueIterateControllerEntryImpl(IterateTaskExecutorFactory<T> executorFactory,
	        Map<String, TaskQueueIterateTaskFactory<T>> tasks,
	        ModelKeyListAccessorFactory<T> accessorFactory) throws IllegalArgumentException {
		super(accessorFactory, tasks);
		this.setExecutorFactory(executorFactory);
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
	public String toString() {
		return "TaskQueueIterateControllerEntryImpl [executorFactory=" + this.executorFactory
		        + ", getAccessorFactory()=" + this.getAccessorFactory() + ", getTasks()=" + this.getTasks() + "]";
	}

}
