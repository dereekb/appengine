package com.dereekb.gae.server.taskqueue.builder;

import java.util.List;

import com.dereekb.gae.server.taskqueue.system.TaskRequest;

/**
 * Generates {@link TaskRequest} instances for the input values.
 *
 * @author dereekb
 *
 * @param <T>
 *            Input type
 */
public interface TaskRequestBuilder<T> {

	public List<TaskRequest> buildRequests(Iterable<T> input);

}
