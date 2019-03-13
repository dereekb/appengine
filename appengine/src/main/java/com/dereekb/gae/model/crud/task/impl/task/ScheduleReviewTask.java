package com.dereekb.gae.model.crud.task.impl.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.TaskRequestSenderImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.utility.TaskQueueIterateRequestBuilderUtility;

/**
 * Abstract extension of {@link TaskRequestSenderImpl} that configures itself to
 * send review tasks to the taskqueue.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 *
 * @see ScheduleCreateReviewTask
 */
public class ScheduleReviewTask<T extends UniqueModel> extends TaskRequestSenderImpl<T> {

	protected ScheduleReviewTask(String taskName, String modelType, TaskScheduler scheduler)
	        throws IllegalArgumentException {
		this(taskName, modelType, scheduler, null);
	}

	protected ScheduleReviewTask(String taskName, String modelType, TaskScheduler scheduler, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		TaskRequestBuilder<T> builder = TaskQueueIterateRequestBuilderUtility.SINGLETON.make(taskName, modelType);
		this.setScheduler(scheduler);
		this.setBuilder(builder);
	}

}
