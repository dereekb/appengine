package com.dereekb.gae.model.crud.task.impl.task;

import java.net.URISyntaxException;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditController;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @see {@link TaskQueueEditController}
 */
public class ScheduleUpdateReviewTask<T extends UniqueModel> extends AbstractScheduleReviewTask<T> {

	private static final TaskRequest DEFAULT_TASK_REQUEST = new TaskRequestImpl(TaskQueueEditController.UPDATE_PATH,
	        Method.PUT);

	public ScheduleUpdateReviewTask(String modelResource, TaskScheduler scheduler) throws URISyntaxException,
	        IllegalArgumentException {
		super(modelResource, scheduler, DEFAULT_TASK_REQUEST);
	}

}
