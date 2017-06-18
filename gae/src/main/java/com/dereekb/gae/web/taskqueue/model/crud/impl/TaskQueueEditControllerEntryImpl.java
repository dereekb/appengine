package com.dereekb.gae.web.taskqueue.model.crud.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.model.crud.TaskQueueEditControllerEntry;

/**
 * {@link TaskQueueEditControllerEntry} implementation. Uses {@link Task}
 * instances to perform the different reviews.
 *
 * @author dereekb
 *
 */
public class TaskQueueEditControllerEntryImpl<T extends UniqueModel>
        implements TaskQueueEditControllerEntry {

	private ModelKeyListAccessorFactory<T> accessorFactory;

	private Task<ModelKeyListAccessor<T>> postCreateTask;
	private Task<ModelKeyListAccessor<T>> postUpdateTask;
	private Task<ModelKeyListAccessor<T>> deleteTask;

	public TaskQueueEditControllerEntryImpl() {}

	public TaskQueueEditControllerEntryImpl(ModelKeyListAccessorFactory<T> accessorFactory) {
		this.setAccessorFactory(accessorFactory);
	}

	public TaskQueueEditControllerEntryImpl(ModelKeyListAccessorFactory<T> accessorFactory,
	        Task<ModelKeyListAccessor<T>> postCreateTask,
	        Task<ModelKeyListAccessor<T>> postUpdateTask,
	        Task<ModelKeyListAccessor<T>> deleteTask) {
		this.setAccessorFactory(accessorFactory);
		this.setPostCreateTask(postCreateTask);
		this.setPostUpdateTask(postUpdateTask);
		this.setDeleteTask(deleteTask);
	}

	public ModelKeyListAccessorFactory<T> getAccessorFactory() {
		return this.accessorFactory;
	}

	public void setAccessorFactory(ModelKeyListAccessorFactory<T> accessorFactory) {
		this.accessorFactory = accessorFactory;
	}

	public Task<ModelKeyListAccessor<T>> getPostCreateTask() {
		return this.postCreateTask;
	}

	public void setPostCreateTask(Task<ModelKeyListAccessor<T>> postCreateTask) {
		this.postCreateTask = postCreateTask;
	}

	public Task<ModelKeyListAccessor<T>> getPostUpdateTask() {
		return this.postUpdateTask;
	}

	public void setPostUpdateTask(Task<ModelKeyListAccessor<T>> postUpdateTask) {
		this.postUpdateTask = postUpdateTask;
	}

	public Task<ModelKeyListAccessor<T>> getDeleteTask() {
		return this.deleteTask;
	}

	public void setDeleteTask(Task<ModelKeyListAccessor<T>> deleteTask) {
		this.deleteTask = deleteTask;
	}

	// MARK: TaskQueueEditControllerEntry
	@Override
	public void reviewCreate(List<ModelKey> keys) {
		if (this.postCreateTask != null) {
			this.postCreateTask.doTask(this.accessorFactory.createAccessor(keys));
		}
	}

	@Override
	public void reviewUpdate(List<ModelKey> keys) {
		if (this.postUpdateTask != null) {
			this.postUpdateTask.doTask(this.accessorFactory.createAccessor(keys));
		}
	}

	@Override
	public void processDelete(List<ModelKey> keys) {
		this.deleteTask.doTask(this.accessorFactory.createAccessor(keys));
	}

	@Override
	public String toString() {
		return "TaskQueueEditControllerEntryImpl [accessorFactory=" + this.accessorFactory + ", postCreateTask="
		        + this.postCreateTask + ", postUpdateTask=" + this.postUpdateTask + ", deleteTask=" + this.deleteTask
		        + "]";
	}

}
