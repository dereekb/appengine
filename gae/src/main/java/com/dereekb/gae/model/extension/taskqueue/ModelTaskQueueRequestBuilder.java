package com.dereekb.gae.model.extension.taskqueue;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueParamPair;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequest;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueRequestBuilder;
import com.dereekb.gae.server.taskqueue.functions.observer.TaskQueueObjectRequestBuilder;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Abstract class for helping build requests for the Task Queue that use
 * {@link UniqueModel} objects.
 *
 * Implementation of the {@link TaskQueueObjectRequestBuilder} interface.
 *
 * @author dereekb
 *
 * @param <T>
 *            Target model.
 */
@Deprecated
public abstract class ModelTaskQueueRequestBuilder<T extends UniqueModel>
        implements TaskQueueModelRequestBuilder<T> {

	private static final String IDENTIFIER_PARAM_NAME = "keys";

	private boolean singleRequest = false;
	private Long countdown;

	/**
	 * @return Returns the request url.
	 */
	protected abstract String getRequestPath();

	/**
	 * Initialized the request. By default, this sets the countdown if the countdown variable is not null.
	 *
	 * @param request
	 */
	protected void initializeRequest(TaskQueuePushRequest request) {
		if (this.countdown != null) {
			request.setCountdown(this.countdown);
		}
	}

	@Override
	public final Collection<TaskQueuePushRequest> buildModelRequests(Iterable<T> objects) {
		List<ModelKey> keys = ModelKey.readModelKeys(objects);
		return this.buildRequests(keys);
	}

	/**
	 * Builds a collection of requests using the input keys.
	 *
	 * @param objects
	 * @return
	 */
	@Override
	public final Collection<TaskQueuePushRequest> buildRequests(Collection<ModelKey> keys) {
		String requestUrl = this.getRequestPath();
		TaskQueuePushRequest request = new TaskQueuePushRequest(requestUrl);
		this.initializeRequest(request);

		TaskQueueParamPair pair = TaskQueueParamPair
.pairWithCommaSeparatedValue(IDENTIFIER_PARAM_NAME, keys);
		request.addParameter(pair);

		Collection<TaskQueuePushRequest> requestCollection = null;

		if (this.singleRequest == false && (keys.size() > 1)) {
			TaskQueueRequestBuilder builder = new TaskQueueRequestBuilder();
			requestCollection = builder.splitTaskAtParameter(IDENTIFIER_PARAM_NAME, request);
		} else {
			requestCollection = SingleItem.withValue(request);
		}

		return requestCollection;
	}

	public boolean isSingleRequest() {
		return this.singleRequest;
	}

	public void setSingleRequest(boolean singleRequest) {
		this.singleRequest = singleRequest;
	}

	public Long getCountdown() {
		return this.countdown;
	}

	public void setCountdown(Long countdown) throws IllegalArgumentException {
		if (countdown != null && countdown < 0) {
			throw new IllegalArgumentException("Cannot have a negative countdown.");
		}

		this.countdown = countdown;
	}

}
