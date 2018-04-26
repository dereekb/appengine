package com.dereekb.gae.model.crud.task.impl.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;

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

	private static final String CREATE_TASK = "create";

	public ScheduleCreateReviewTask(String modelType, TaskScheduler scheduler) throws IllegalArgumentException {
		this(modelType, scheduler, null);
	}

	public ScheduleCreateReviewTask(String modelType, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(CREATE_TASK, modelType, scheduler, baseRequest);
	}

}
