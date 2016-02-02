package com.dereekb.gae.model.extension.search.query.search.components;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;

/**
 * Used to convert queries into {@link ObjectifyQueryRequest} instances.
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public interface ModelQueryConverter<T extends ObjectifyModel<T>, Q> {

	/**
	 * Generates a {@link ObjectifyQueryRequest} instance from the input query.
	 */
	public ObjectifyQueryRequest<T> convertQuery(Q query);

}
