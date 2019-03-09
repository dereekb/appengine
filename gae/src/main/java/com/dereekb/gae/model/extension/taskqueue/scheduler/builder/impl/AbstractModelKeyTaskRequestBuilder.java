package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.List;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.ListAccessorTaskRequestBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.PartitionedTaskRequestBuilder;

/**
 * Abstract {@link ListAccessorTaskRequestBuilder} and
 * {@link TaskRequestBuilder} that only uses the input
 * {@link ModelKey} values.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see AbstractModelTaskRequestBuilder for model task building.
 */
public abstract class AbstractModelKeyTaskRequestBuilder<T extends UniqueModel> extends PartitionedTaskRequestBuilder<ModelKey>
        implements TaskRequestBuilder<T>, ListAccessorTaskRequestBuilder<T> {

	public AbstractModelKeyTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(baseRequest);
	}

	public AbstractModelKeyTaskRequestBuilder(boolean asIndividualRequests, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(asIndividualRequests, baseRequest);
	}

	// MARK: TaskRequestBuilder
	@Override
	public List<MutableTaskRequest> buildRequests(Iterable<T> input) {
		return this.buildRequestsWithInput(ModelKey.readModelKeys(input));
	}

	// MARK: ListAccessorTaskRequestBuilder
	@Override
	public List<MutableTaskRequest> buildRequests(ModelKeyListAccessor<T> input) {
		List<ModelKey> modelKeys = input.getModelKeys();
		return this.buildRequestsWithInput(modelKeys);
	}

}
