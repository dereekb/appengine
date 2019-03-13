package com.dereekb.gae.server.taskqueue.task;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * Used for "forwarding" a taskqueue request.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TaskQueueForwardTaskFactory<T extends UniqueModel>
        implements Task<ModelKeyListAccessor<T>>, TaskQueueIterateTaskFactory<T> {

	private TaskRequestSender<T> forwardSender;

	public TaskQueueForwardTaskFactory(TaskRequestSender<T> forwardSender) {
		this.setForwardSender(forwardSender);
	}

	public TaskRequestSender<T> getForwardSender() {
		return this.forwardSender;
	}

	public void setForwardSender(TaskRequestSender<T> forwardSender) {
		if (forwardSender == null) {
			throw new IllegalArgumentException("forwardSender cannot be null.");
		}

		this.forwardSender = forwardSender;
	}

	// MARK: TaskQueueIterateTaskFactory<T>
	@Override
	public Task<ModelKeyListAccessor<T>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		return this;
	}

	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		this.forwardSender.sendTasks(input.getModels());
	}

}
