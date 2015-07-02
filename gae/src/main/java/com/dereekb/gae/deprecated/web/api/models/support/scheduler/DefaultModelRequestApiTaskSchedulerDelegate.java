package com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler;

import java.util.Collection;
import java.util.List;

import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.model.extension.taskqueue.TaskQueueModelRequestBuilder;
import com.thevisitcompany.gae.server.taskqueue.TaskQueuePushRequest;

/**
 * Helper class for using a {@link TaskQueueModelRequestBuilder} to build requests using the {@link ApiTaskSchedulerRequest} identifiers.
 * 
 * @author dereekb
 */
public class DefaultModelRequestApiTaskSchedulerDelegate<T extends KeyedModel<Long>>
        implements ApiTaskSchedulerDelegate {

	private TaskQueueModelRequestBuilder<T, Long> builder;

	@Override
	public Collection<TaskQueuePushRequest> createRequests(ApiTaskSchedulerRequest request)
	        throws ApiTaskUnavailableException {
		List<Long> identifiers = request.getIdentifiers();
		return builder.buildRequests(identifiers);
	}

	public TaskQueueModelRequestBuilder<T, Long> getBuilder() {
		return builder;
	}

	public void setBuilder(TaskQueueModelRequestBuilder<T, Long> builder) {
		this.builder = builder;
	}

}
