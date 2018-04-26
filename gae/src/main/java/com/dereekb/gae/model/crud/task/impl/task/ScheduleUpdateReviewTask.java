package com.dereekb.gae.model.crud.task.impl.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;

/**
 * Pre-configured {@link ScheduleReviewTask} for reviewing model updates in
 * the taskqueue.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ScheduleUpdateReviewTask<T extends UniqueModel> extends ScheduleReviewTask<T> {

	private static final String UPDATE_TASK = "update";

	public ScheduleUpdateReviewTask(String modelType, TaskScheduler scheduler) throws IllegalArgumentException {
		this(modelType, scheduler, null);
	}

	public ScheduleUpdateReviewTask(String modelType, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(UPDATE_TASK, modelType, scheduler, baseRequest);
	}

}
