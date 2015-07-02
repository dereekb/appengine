package com.dereekb.gae.server.taskqueue.builder;

import java.util.List;

import com.dereekb.gae.server.taskqueue.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestSystem;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Implementation of {@link TaskRequestSender} using a
 * {@link TaskRequestBuilder} and {@link TaskRequestSystem}.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type
 */
public class TaskRequestSenderImpl<T>
        implements TaskRequestSender<T> {

	private TaskRequestBuilder<T> builder;
	private TaskRequestSystem system;

	public TaskRequestSenderImpl() {}

	public TaskRequestSenderImpl(TaskRequestBuilder<T> builder, TaskRequestSystem system) {
		this.builder = builder;
		this.system = system;
	}

	public TaskRequestBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(TaskRequestBuilder<T> builder) {
		this.builder = builder;
	}

	public TaskRequestSystem getSystem() {
		return this.system;
	}

	public void setSystem(TaskRequestSystem system) {
		this.system = system;
	}

	@Override
	public void sendTask(T input) throws SubmitTaskException {
		this.sendTasks(SingleItem.withValue(input));
	}

	@Override
	public void sendTasks(Iterable<T> input) throws SubmitTaskException {
		List<TaskRequest> requests = this.builder.buildRequests(input);
		this.system.submitRequests(requests);
	}

}
