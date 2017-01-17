package com.dereekb.gae.utilities.misc.parameters;

import java.util.Map;

/**
 * {@link Parameters} implementation that can be modified with a {@link Map}.
 * 
 * @author dereekb
 *
 */
public interface MutableParameters
        extends Parameters {

	/**
	 * Sets the parameters.
	 * 
	 * @param parameters
	 *            {@link Map}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             thrown if one or more parameters in the map are illegal.
	 */
	public void setParameters(Map<String, String> parameters) throws IllegalArgumentException;

}
