package com.dereekb.gae.server.taskqueue.functions.factory;

import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueManager;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueManagerFactory;
import com.dereekb.gae.server.taskqueue.functions.observer.TaskQueueFunctionObserver;
import com.dereekb.gae.server.taskqueue.functions.observer.TaskQueueObjectRequestBuilder;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.observer.AbstractStagedFunctionObserverFactory;

@Deprecated
public class TaskQueueFunctionObserverFactory<T> extends AbstractStagedFunctionObserverFactory<TaskQueueFunctionObserver<T>, T> {

	private TaskQueueObjectRequestBuilder<T> delegate;

	private TaskQueueManager sharedManager;
	private TaskQueueManagerFactory managerFactory;

	public TaskQueueFunctionObserverFactory() {
		super(StagedFunctionStage.FUNCTION_FINISHED);
	}

	@Override
	public TaskQueueFunctionObserver<T> generateObserver() {
		TaskQueueFunctionObserver<T> observer = new TaskQueueFunctionObserver<T>();

		if (this.managerFactory != null) {
			TaskQueueManager manager = this.managerFactory.make();
			observer.setManager(manager);
		} else {
			if (this.sharedManager != null) {
				observer.setManager(this.sharedManager);
			}
		}

		observer.setDelegate(this.delegate);
		return observer;
	}

	public TaskQueueObjectRequestBuilder<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(TaskQueueObjectRequestBuilder<T> delegate) {
		this.delegate = delegate;
	}

	public TaskQueueManager getSharedManager() {
		return this.sharedManager;
	}

	public void setSharedManager(TaskQueueManager sharedManager) {
		this.sharedManager = sharedManager;
	}

	public TaskQueueManagerFactory getManagerFactory() {
		return this.managerFactory;
	}

	public void setManagerFactory(TaskQueueManagerFactory managerFactory) {
		this.managerFactory = managerFactory;
	}

}