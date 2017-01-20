package com.dereekb.gae.utilities.query.builder.parameters;

/**
 * {@link EncodedQueryParameter} implementation that is also keyed by a string
 * value.
 * 
 * @author dereekb
 *
 */
public interface KeyedEncodedQueryParameter
        extends EncodedQueryParameter {

	/**
	 * Returns a key for this parameter.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getParameterKey();

}
