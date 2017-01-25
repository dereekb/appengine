package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.AbstractTaskRequestBuilder;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;

/**
 * Implementation of {@link TaskRequestBuilder} that generates tasks keyed to
 * {@link ModelKey} of the input {@link UniqueModel} instances.
 * <p>
 * This builder uses a {@link TaskRequest} template to copy from, and appends
 * the request identifiers.
 * <p>
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class KeyParameterTaskRequestBuilder<T extends UniqueModel> extends AbstractTaskRequestBuilder<T> {

	public static final String DEFAULT_IDENTIFIER_PARAM_NAME = "keys";

	/**
	 * The parameter name to use.
	 */
	private String idParameter = DEFAULT_IDENTIFIER_PARAM_NAME;

	public KeyParameterTaskRequestBuilder(TaskRequest baseRequest) {
		super(baseRequest);
	}

	public String getIdParameter() {
		return this.idParameter;
	}

	public void setIdParameter(String idParameter) {
		if (idParameter == null) {
			throw new IllegalArgumentException("idParameter cannot be null.");
		}

		this.idParameter = idParameter;
	}

	// MARK: TaskRequestBuilder
	@Override
	protected Collection<KeyedEncodedParameter> buildRequestParameters(List<T> partition) {
		List<ModelKey> keys = ModelKey.readModelKeys(partition);
		KeyedEncodedParameterImpl keyParameter = KeyedEncodedParameterImpl.make(this.idParameter, keys);
		return new SingleItem<KeyedEncodedParameter>(keyParameter);
	}

}
