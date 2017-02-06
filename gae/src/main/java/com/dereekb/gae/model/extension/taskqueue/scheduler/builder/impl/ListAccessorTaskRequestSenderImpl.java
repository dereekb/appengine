package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.ListAccessorTaskRequestBuilder;
import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.ListAccessorTaskRequestSender;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.AbstractTaskRequestSender;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link ListAccessorTaskRequestSender} implementation.
 * 
 * Also implements {@link Task} for {@link ModelKeyListAccessor} types.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ListAccessorTaskRequestSenderImpl<T extends UniqueModel> extends AbstractTaskRequestSender
        implements ListAccessorTaskRequestSender<T>, Task<ModelKeyListAccessor<T>> {

	private ListAccessorTaskRequestBuilder<T> builder;

	protected ListAccessorTaskRequestSenderImpl() {}

	public ListAccessorTaskRequestSenderImpl(ListAccessorTaskRequestBuilder<T> builder, TaskScheduler scheduler) {
		super(scheduler);
		this.setBuilder(builder);
	}

	public ListAccessorTaskRequestBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(ListAccessorTaskRequestBuilder<T> builder) throws IllegalArgumentException {
		if (builder == null) {
			throw new IllegalArgumentException("Builder cannot be null");
		}

		this.builder = builder;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		this.sendTask(input);
	}

	// MARK: ListAccessorTaskRequestSender
	@Override
	public void sendTask(ModelKeyListAccessor<T> accessor) throws SubmitTaskException {
		Collection<MutableTaskRequest> requests = this.builder.buildRequests(accessor);
		this.scheduleTasks(requests);
	}

}
