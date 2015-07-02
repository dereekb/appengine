package com.dereekb.gae.server.taskqueue.builder;

import java.util.List;

import com.dereekb.gae.server.taskqueue.system.TaskRequest;

/**
 * Used for splitting up a single request into multiple requests comma-separated
 * parameters.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link TaskRequest} type created.
 * @throws IllegalArgumentException
 *             if the parameter does not exist in the request.
 */
public interface TaskRequestSplitter<T extends TaskRequest> {

	public List<T> splitRequestAtParameter(String parameter,
	                                       TaskRequest request) throws IllegalArgumentException;

}
