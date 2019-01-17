package com.dereekb.gae.server.datastore.models.query.iterator;

import java.util.Map;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used for generating {@link IndexedModelQueryIterable} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexedModelQueryIterableFactory<T extends UniqueModel> {

	public IndexedModelQueryIterable<T> makeIterable();

	public IndexedModelQueryIterable<T> makeIterable(String cursor);

	public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters,
	                                                 String cursor);

}
