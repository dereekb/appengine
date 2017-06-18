package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

/**
 * {@link AbstractKeyQueryIterateTaskRequestBuilder} implementation that uses a
 * delegate to build the request.
 * 
 * @author dereekb
 *
 */
public class QueryIterateTaskRequestBuilder<T extends UniqueModel> extends AbstractKeyQueryIterateTaskRequestBuilder<T> {

	private QueryIterateTaskRequestBuilderDelegate delegate;

	public QueryIterateTaskRequestBuilder(TaskRequest baseRequest, QueryIterateTaskRequestBuilderDelegate delegate)
	        throws IllegalArgumentException {
		super(baseRequest);
		this.setDelegate(delegate);
	}

	public QueryIterateTaskRequestBuilder(boolean asIndividualRequests,
	        TaskRequest baseRequest,
	        QueryIterateTaskRequestBuilderDelegate delegate) throws IllegalArgumentException {
		super(asIndividualRequests, baseRequest);
		this.setDelegate(delegate);
	}

	public QueryIterateTaskRequestBuilderDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(QueryIterateTaskRequestBuilderDelegate delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: PartitionedTaskRequestBuilder
	@Override
	protected EncodedQueryParameters getParametersForPartition(List<ModelKey> partition) {
		return this.delegate.getParameters(partition);
	}

	@Override
	public String toString() {
		return "QueryIterateTaskRequestBuilder [delegate=" + this.delegate + "]";
	}

}
