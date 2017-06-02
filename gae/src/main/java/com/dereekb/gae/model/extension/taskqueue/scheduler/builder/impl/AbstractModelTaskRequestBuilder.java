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
 * @see AbstractModelKeyTaskRequestBuilder for key-only task building.
 */
public abstract class AbstractModelTaskRequestBuilder<T extends UniqueModel> extends PartitionedTaskRequestBuilder<T>
        implements TaskRequestBuilder<T>, ListAccessorTaskRequestBuilder<T> {

	public AbstractModelTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(baseRequest);
	}

	public AbstractModelTaskRequestBuilder(boolean asIndividualRequests, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(asIndividualRequests, baseRequest);
	}

	// MARK: TaskRequestBuilder
	@Override
	public List<MutableTaskRequest> buildRequests(Iterable<T> input) {
		return this.buildRequestsWithInput(input);
	}

	// MARK: ListAccessorTaskRequestBuilder
	@Override
	public List<MutableTaskRequest> buildRequests(ModelKeyListAccessor<T> input) {
		List<T> models = input.getModels();
		return this.buildRequestsWithInput(models);
	}

}
