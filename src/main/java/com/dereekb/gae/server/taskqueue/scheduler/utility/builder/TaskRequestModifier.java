package com.dereekb.gae.server.taskqueue.scheduler.utility.builder;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;

/**
 * Used for modifying {@link MutableTaskRequest} values.
 * 
 * @author dereekb
 *
 */
public interface TaskRequestModifier {

	/**
	 * Modifies the input request.
	 * 
	 * @param request
	 *            Request. Never {@code null}.
	 * @return {@link MutableTaskRequest}. Never {@code null}.
	 */
	public MutableTaskRequest modifyRequest(MutableTaskRequest request);

	/**
	 * Modifies the input requests and outputs the result. The input requests
	 * and collection may or may not be returned as the result, meaning that
	 * only the returned collection should be used instead of relying on the
	 * input collection to be modified.
	 * 
	 * @param requests
	 *            Requests collection. Never {@code null}.
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<MutableTaskRequest> modifyRequests(Collection<? extends MutableTaskRequest> requests);

}
