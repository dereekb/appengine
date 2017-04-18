package com.dereekb.gae.model.crud.task.modelkey.impl;

import java.net.URISyntaxException;

import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleReviewTask;
import com.dereekb.gae.model.crud.task.modelkey.ModelKeyDeleteTask;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.model.crud.TaskQueueEditController;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Pre-configured {@link ScheduleReviewTask} and {@link DeleteTask} for
 * performing deletions in the taskqueue.
 * <p>
 * Generally used to queue up requests to {@link TaskQueueEditController}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see TaskQueueEditController
 */
public class ScheduleModelKeyDeleteTask extends ScheduleReviewTask<ModelKey>
        implements ModelKeyDeleteTask {

	public ScheduleModelKeyDeleteTask(String modelResource, TaskScheduler scheduler) throws URISyntaxException {
		this(modelResource, scheduler, new TaskRequestImpl("delete", Method.DELETE));
	}

	public ScheduleModelKeyDeleteTask(String modelResource, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws URISyntaxException {
		super(modelResource, baseRequest, scheduler);
	}

	// MARK: ModelKeyDeleteTask
	@Override
	public void doTask(Iterable<ModelKey> input) throws FailedTaskException {
		super.sendTasks(input);
	}

}
