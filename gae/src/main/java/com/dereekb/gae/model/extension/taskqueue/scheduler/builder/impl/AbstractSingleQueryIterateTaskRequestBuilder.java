package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

/**
 * {@link AbstractQueryIterateTaskRequestBuilder} extension that only allows
 * individual requests.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractSingleQueryIterateTaskRequestBuilder<T extends UniqueModel> extends AbstractQueryIterateTaskRequestBuilder<T> {

	public AbstractSingleQueryIterateTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(true, baseRequest);
	}

	// MARK: Override
	@Override
	public final void setAsIndividualRequests(boolean requests) {
		if (requests == false) {
			throw new IllegalArgumentException("Only individual requests are allowed for this type.");
		}

		super.setAsIndividualRequests(requests);
	}

	// MARK:
	@Override
	protected Collection<? extends KeyedEncodedParameter> buildRequestParameters(List<ModelKey> partition) {
		EncodedQueryParameters parameters = this.getParametersForModelKey(partition.get(0));
		return ParameterUtility.toParametersList(parameters);
	}

	protected abstract EncodedQueryParameters getParametersForModelKey(ModelKey modelKey);

	@Override
	protected final EncodedQueryParameters getParametersForPartition(List<ModelKey> partition) {
		throw new UnsupportedOperationException("Not usable from this type.");
	}

}
