package com.dereekb.gae.utilities.query.builder.parameters;

import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder;

/**
 * Parameter for queries that can be broken into a string for external use.
 * 
 * @author dereekb
 * @see {@link QueryFieldParameterDencoder} for encoding.
 */
public interface EncodedQueryParameter {

	/**
	 * Returns the parameter string.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getParameterString();

}
