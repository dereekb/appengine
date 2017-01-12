package com.dereekb.gae.utilities.query.builder.parameters;

import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder;

/**
 * {@link QueryParameter} that can be configured by using a parameter string.
 *
 * @author dereekb
 * @see {@link QueryFieldParameterDencoder} for encoding/decoding.
 */
public interface ConfigurableQueryParameter
        extends QueryParameter {

	/**
	 * Configures this {@link ConfigurableQueryParamter} using the input string.
	 *
	 * @param parameterString
	 *            {@link String}. Never {@code null}.
	 * @throws IllegalArgumentExceptions
	 *             If the string is improperly formatted.
	 */
	public void setParameterString(String parameterString) throws IllegalArgumentException;

}
