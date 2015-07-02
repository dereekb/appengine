package com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler;

import java.util.Collection;

import com.thevisitcompany.gae.server.taskqueue.TaskQueueParamPair;
import com.thevisitcompany.gae.server.taskqueue.TaskQueuePushRequest;
import com.thevisitcompany.gae.utilities.collections.SingleItem;

/**
 * Basic implementation of the {@link ApiTaskSchedulerDelegate} that creates a static request.
 *
 * @author dereekb
 */
public class StaticApiTaskSchedulerDelegate
        implements ApiTaskSchedulerDelegate {

	protected String url;
	protected Long countdown;
	protected Collection<TaskQueueParamPair> pairs;

	@Override
	public Collection<TaskQueuePushRequest> createRequests(ApiTaskSchedulerRequest request)
	        throws ApiTaskUnavailableException {
		TaskQueuePushRequest pushRequest = this.createPushRequest(request);
		return SingleItem.withValue(pushRequest);
	}

	protected TaskQueuePushRequest createPushRequest(ApiTaskSchedulerRequest request) {
		TaskQueuePushRequest pushRequest = new TaskQueuePushRequest(this.url);
		pushRequest.setCountdown(this.countdown);
		pushRequest.setParameters(this.pairs);
		return pushRequest;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCountdown() {
		return this.countdown;
	}

	public void setCountdown(Long countdown) {
		this.countdown = countdown;
	}

	public Collection<TaskQueueParamPair> getPairs() {
		return this.pairs;
	}

	public void setPairs(Collection<TaskQueueParamPair> pairs) {
		this.pairs = pairs;
	}

}
