package com.dereekb.gae.server.taskqueue.scheduler.utility.builder;

import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;

/**
 * Generates {@link TaskRequest} instances for the input values.
 *
 * @author dereekb
 *
 * @param <T>
 *            Input type
 */
public interface TaskRequestBuilder<T> {

	public List<MutableTaskRequest> buildRequests(Iterable<T> input);

}
