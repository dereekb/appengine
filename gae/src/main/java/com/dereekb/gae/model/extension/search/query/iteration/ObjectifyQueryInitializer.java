package com.dereekb.gae.model.extension.search.query.iteration;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;

/**
 * Interface used in conjunction with an {@link IterableObjectifyModelQuery} to modify the query before it is iterated over.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public interface ObjectifyQueryInitializer<T> {

	/**
	 * Initializes the target query.
	 *
	 * @param query
	 */
	public void initialize(ObjectifyQuery<T> query);

	/**
	 * Sets the custom parameters to initialize with.
	 *
	 * @param parameters
	 */
	public void setParameters(Map<String, String> parameters);

}
