package com.dereekb.gae.server.taskqueue.functions.observer;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueManager;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequest;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Observer that runs a set of task queues when called.
 *
 * @author dereekb
 */
@Deprecated
public class TaskQueueFunctionObserver<T>
        implements StagedFunctionObserver<T> {

	private TaskQueueObjectRequestBuilder<T> delegate;
	private TaskQueueManager manager;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {
		List<T> objects = handler.getFunctionObjects();
		Collection<TaskQueuePushRequest> requests = this.delegate.buildModelRequests(objects);

		if (requests.isEmpty() == false) {
			this.manager.add(requests);
		}
	}

	public TaskQueueObjectRequestBuilder<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(TaskQueueObjectRequestBuilder<T> delegate) {
		this.delegate = delegate;
	}

	public TaskQueueManager getManager() {
		return this.manager;
	}

	public void setManager(TaskQueueManager manager) {
		this.manager = manager;
	}

}
