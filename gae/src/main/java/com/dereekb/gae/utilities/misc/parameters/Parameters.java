package com.dereekb.gae.utilities.misc.parameters;

import java.util.Map;

/**
 * Type that can expose it's data with string parameters.
 * 
 * @author dereekb
 *
 */
public interface Parameters {

	/**
	 * Gets the parameters.
	 * 
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, String> getParameters();

}
