package com.dereekb.gae.model.crud.task.impl.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditController;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Pre-configured {@link ScheduleReviewTask} for reviewing model creations in
 * the taskqueue.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ScheduleCreateReviewTask<T extends UniqueModel> extends ScheduleReviewTask<T> {

	private static final TaskRequest DEFAULT_TASK_REQUEST = new TaskRequestImpl(TaskQueueEditController.CREATE_PATH,
	        Method.PUT);

	public ScheduleCreateReviewTask(String modelResource, TaskScheduler scheduler) throws IllegalArgumentException {
		super(modelResource, DEFAULT_TASK_REQUEST, scheduler);
	}

}
