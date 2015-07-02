package com.dereekb.gae.server.taskqueue.functions.observer;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequest;

/**
 * Delegate for a {@link TaskQueueFunctionObserver} that is used to build a set of requests given the input models.
 *
 * @author dereekb
 */
@Deprecated
public interface TaskQueueObjectRequestBuilder<T> {

	/**
	 * Builds requests using the given objects.
	 *
	 * Does not always have to return a collection of requests for a collection of objects; a single request can be used
	 * for a set of objects.
	 *
	 * @param objects
	 * @return
	 */
	public Collection<TaskQueuePushRequest> buildModelRequests(Iterable<T> objects);

}
