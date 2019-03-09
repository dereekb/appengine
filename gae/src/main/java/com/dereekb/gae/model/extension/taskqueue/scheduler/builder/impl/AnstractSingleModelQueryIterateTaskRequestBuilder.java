package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

/**
 * {@link AbstractModelTaskRequestBuilder} that only allows individual requests.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AnstractSingleModelQueryIterateTaskRequestBuilder<T extends UniqueModel> extends AbstractModelTaskRequestBuilder<T> {

	public AnstractSingleModelQueryIterateTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
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

	// MARK: AbstractModelTaskRequestBuilders
	@Override
	protected Collection<? extends KeyedEncodedParameter> buildRequestParameters(List<T> partition) {
		return this.buildRequestParametersForModel(partition.get(0));
	}

	protected Collection<? extends KeyedEncodedParameter> buildRequestParametersForModel(T model) {
		EncodedQueryParameters parameters = this.getParametersForModel(model);
		Collection<? extends KeyedEncodedParameter> encoded = ParameterUtility.toParametersList(parameters);
		return encoded;
	}

	protected abstract EncodedQueryParameters getParametersForModel(T model);

}
