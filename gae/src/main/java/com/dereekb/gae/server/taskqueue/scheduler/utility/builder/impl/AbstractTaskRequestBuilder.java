package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;

/**
 * {@link TaskRequestBuilder} implementation that extends
 * {@link PartitionedTaskRequestBuilder} and provides support for buidling
 * requests given the input.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractTaskRequestBuilder<T> extends PartitionedTaskRequestBuilder<T>
        implements TaskRequestBuilder<T> {

	public AbstractTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(baseRequest);
	}

	public AbstractTaskRequestBuilder(boolean asIndividualRequests, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(asIndividualRequests, baseRequest);
	}

	@Override
	public List<MutableTaskRequest> buildRequests(Iterable<T> input) {
		return super.buildRequestsWithInput(input);
	}

}
