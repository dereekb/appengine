package com.dereekb.gae.model.crud.task.impl.task;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.KeyParameterTaskRequestBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.TaskRequestResourceModifier;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.TaskRequestSenderImpl;

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

	protected ScheduleReviewTask(String modelResource, TaskRequest baseRequest, TaskScheduler scheduler)
	        throws IllegalArgumentException {
		this.setScheduler(scheduler);
		this.setBuilder(new KeyParameterTaskRequestBuilder<T>(baseRequest));
		this.setModifier(new TaskRequestResourceModifier(modelResource));
	}

}
