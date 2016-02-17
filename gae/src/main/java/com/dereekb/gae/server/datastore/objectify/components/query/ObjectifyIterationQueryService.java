package com.dereekb.gae.server.datastore.objectify.components.query;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;


public interface ObjectifyIterationQueryService<T extends ObjectifyModel<T>> {

	/**
	 * Creates a new {@link IterableObjectifyQuery} instance.
	 */
	public ObjectifyQueryIterableFactory<T> makeIterableQueryFactory();

}
