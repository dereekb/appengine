package com.dereekb.gae.server.taskqueue.builder;

import com.dereekb.gae.server.taskqueue.system.TaskRequest;

/**
 * Used for copying {@link TaskRequest} instances.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface TaskRequestCopier<T extends TaskRequest> {

	/**
	 * Makes a full copy of the request including parameters, headers. The name
	 * is still not copied.
	 *
	 * Parameters and headers are copied into new collections.
	 *
	 * @param request
	 * @return copy of the input request.
	 */
	public T fullyCopyRequest(TaskRequest request);

	/**
	 * Makes a partial copy of the request, copying only the method type, url
	 * and timings.
	 *
	 * @param request
	 * @return copy of the input request.
	 */
	public T partialCopyRequest(TaskRequest request);

}
