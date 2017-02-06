package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

/**
 * {@link QueryIterateTaskRequestBuilder} delegate.
 * 
 * @author dereekb
 *
 */
public interface QueryIterateTaskRequestBuilderDelegate {

	/**
	 * Creates a query to send to the task queue using the input keys.
	 * 
	 * @param partition
	 *            {@link List}. Never {@code null}.
	 * @return {@link EncodedQueryParameters}. Never {@code null}.
	 */
	public EncodedQueryParameters getParameters(List<ModelKey> partition);

}
