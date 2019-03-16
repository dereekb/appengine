package com.dereekb.gae.model.crud.task.modelkey.impl;

import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleReviewTask;
import com.dereekb.gae.model.crud.task.modelkey.ModelKeyDeleteTask;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

/**
 * Pre-configured {@link ScheduleReviewTask} and {@link DeleteTask} for
 * performing deletions in the taskqueue.
 * <p>
 * used to queue up requests to {@link TaskQueueIterateController}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ScheduleModelKeyDeleteTask extends ScheduleReviewTask<ModelKey>
        implements ModelKeyDeleteTask {

	private static final String DELETE_TASK = "delete";

	public ScheduleModelKeyDeleteTask(String modelType, TaskScheduler scheduler) throws IllegalArgumentException {
		this(modelType, scheduler, null);
	}

	public ScheduleModelKeyDeleteTask(String modelType, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(DELETE_TASK, modelType, scheduler, baseRequest);
	}

	// MARK: ModelKeyDeleteTask
	@Override
	public void doTask(Iterable<ModelKey> input) throws FailedTaskException {
		super.sendTasks(input);
	}

}
