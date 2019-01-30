package com.dereekb.gae.utilities.query.builder;

import java.util.Map;

import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

/**
 * Factory used for making query objects.
 * 
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public interface QueryFactory<Q extends EncodedQueryParameters> {

	/**
	 * Creates a new query.
	 * 
	 * @param parameters
	 *            {@link Map} of parameters. Never {@code null}.
	 * @return Instance of query. Never {@code null}.
	 */
	public Q makeQuery(Map<String, String> parameters) throws IllegalArgumentException;

}
