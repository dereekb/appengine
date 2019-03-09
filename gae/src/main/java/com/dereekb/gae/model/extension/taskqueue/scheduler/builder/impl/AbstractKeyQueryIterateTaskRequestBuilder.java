package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

/**
 * Special {@link TaskRequestBuilder} implementation that takes in a
 * {@link ModelKeyListAccessor}, passes it to a delegate to generate a
 * {@link EncodedQueryParameters} instance and creates a
 * {@link MutableTaskRequest}. Due to this, the input {@link ModelKey} may or
 * may not refer to the same type that the queue will be executed against.
 * <p>
 * By default has a default partition size of 10.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractKeyQueryIterateTaskRequestBuilder<T extends UniqueModel> extends AbstractModelKeyTaskRequestBuilder<T> {

	public static final Integer DEFAULT_QUERY_KEY_PARTITION_SIZE = 10;

	private static final Partitioner DEFAULT_KEY_PARTITIONER = new PartitionerImpl(DEFAULT_QUERY_KEY_PARTITION_SIZE);

	public AbstractKeyQueryIterateTaskRequestBuilder(boolean asIndividualRequests, TaskRequest baseRequest)
	        throws IllegalArgumentException {
		super(asIndividualRequests, baseRequest);
	}

	public AbstractKeyQueryIterateTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(baseRequest);
	}

	// MARK: Override
	@Override
	protected Partitioner createDefaultPartitioner() {
		return DEFAULT_KEY_PARTITIONER;
	}

	// MARK: PartitionedTaskRequestBuilder
	@Override
	protected Collection<? extends KeyedEncodedParameter> buildRequestParameters(List<ModelKey> partition) {
		EncodedQueryParameters parameters = this.getParametersForPartition(partition);
		return ParameterUtility.toParametersList(parameters);
	}

	protected abstract EncodedQueryParameters getParametersForPartition(List<ModelKey> partition);

}
