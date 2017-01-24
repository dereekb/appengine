package com.dereekb.gae.utilities.misc.parameters;

/**
 * {@link EncodedParameter} that can be configured by using a parameter
 * string.
 *
 * @author dereekb
 */
public interface ConfigurableEncodedParameter
        extends KeyedEncodedParameter {

	/**
	 * Configures this {@link ConfigurableEncodedParamter} using the input
	 * string.
	 *
	 * @param parameterString
	 *            {@link String}. Never {@code null}.
	 * @throws IllegalArgumentExceptions
	 *             If the string is improperly formatted.
	 */
	public void setParameterString(String parameterString) throws IllegalArgumentException;

}
