package com.dereekb.gae.server.datastore.objectify.query.builder.parameters;


/**
 * {@link QueryParameter} that can be configured by using a parameter string.
 *
 * @author dereekb
 *
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
