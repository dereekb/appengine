package com.dereekb.gae.web.taskqueue.controller.crud.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditControllerEntry;

/**
 * {@link TaskQueueEditControllerEntry} implementation. Uses {@link Task}
 * instances to perform the different reviews.
 *
 * @author dereekb
 *
 */
public class TaskQueueEditControllerEntryImpl
        implements TaskQueueEditControllerEntry {

	private IterableTask<ModelKey> reviewCreateTask;
	private IterableTask<ModelKey> reviewUpdateTask;
	private IterableTask<ModelKey> reviewDeleteTask;

	public TaskQueueEditControllerEntryImpl() {}

	public TaskQueueEditControllerEntryImpl(IterableTask<ModelKey> reviewCreateTask,
	        IterableTask<ModelKey> reviewUpdateTask,
	        IterableTask<ModelKey> reviewDeleteTask) {
		this.reviewCreateTask = reviewCreateTask;
		this.reviewUpdateTask = reviewUpdateTask;
		this.reviewDeleteTask = reviewDeleteTask;
	}

	public IterableTask<ModelKey> getReviewCreateTask() {
		return this.reviewCreateTask;
	}

	public void setReviewCreateTask(IterableTask<ModelKey> reviewCreateTask) {
		this.reviewCreateTask = reviewCreateTask;
	}

	public IterableTask<ModelKey> getReviewUpdateTask() {
		return this.reviewUpdateTask;
	}

	public void setReviewUpdateTask(IterableTask<ModelKey> reviewUpdateTask) {
		this.reviewUpdateTask = reviewUpdateTask;
	}

	public IterableTask<ModelKey> getReviewDeleteTask() {
		return this.reviewDeleteTask;
	}

	public void setReviewDeleteTask(IterableTask<ModelKey> reviewDeleteTask) {
		this.reviewDeleteTask = reviewDeleteTask;
	}

	// MARK: TaskQueueEditControllerEntry
	@Override
	public void reviewCreate(List<ModelKey> keys) {
		if (this.reviewCreateTask != null) {
			this.reviewCreateTask.doTask(keys);
		}
	}

	@Override
	public void reviewUpdate(List<ModelKey> keys) {
		if (this.reviewUpdateTask != null) {
			this.reviewUpdateTask.doTask(keys);
		}
	}

	@Override
	public void reviewDelete(List<ModelKey> keys) {
		if (this.reviewDeleteTask != null) {
			this.reviewDeleteTask.doTask(keys);
		}
	}

	@Override
	public String toString() {
		return "TaskQueueEditControllerEntryImpl [reviewCreateTask=" + this.reviewCreateTask + ", reviewUpdateTask="
		        + this.reviewUpdateTask + ", reviewDeleteTask=" + this.reviewDeleteTask + "]";
	}

}
