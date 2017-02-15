package com.dereekb.gae.utilities.misc.parameters;

import java.util.Map;

/**
 * Type that can be represented by a map of parameters.
 * 
 * @author dereekb
 *
 */
public interface Parameters {

	/**
	 * Gets a map container all string encoded parameters.
	 * 
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, String> getParameters();

}
