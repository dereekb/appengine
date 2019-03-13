package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * {@link AbstractTaskRequestBuilder} extension for individual input.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractIndividualTaskRequestBuilder<T> extends AbstractTaskRequestBuilder<T> {

	public AbstractIndividualTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(true, baseRequest);
	}

	@Override
	public final void setAsIndividualRequests(boolean asIndividualRequests) {
		super.setAsIndividualRequests(true);
	}

	// MARK: AbstractTaskRequestBuilder<T>
	@Override
	protected TaskRequestImpl buildRequest(List<T> partition) {
		TaskRequestImpl request = super.buildRequest(partition);

		this.buildRequest(request, partition.get(0));

		return request;
	}

	protected TaskRequestImpl buildRequest(TaskRequestImpl request, T entity) {
		// Optionally override in subclass
		return request;
	}

	@Override
	protected Collection<? extends KeyedEncodedParameter> buildRequestParameters(List<T> partition) {
		return this.buildRequestParameters(partition.get(0));
	}

	protected abstract Collection<? extends KeyedEncodedParameter> buildRequestParameters(T entity);

}
