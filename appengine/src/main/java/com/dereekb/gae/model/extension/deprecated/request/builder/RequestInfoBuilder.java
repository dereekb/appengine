package com.dereekb.gae.model.extension.request.builder;

import java.util.HashMap;

/**
 * Used to build the info map for a {@link Request}.
 *
 * @author dereekb
 *
 * @param <T>
 *            Input type.
 */
public interface RequestInfoBuilder<T> {

	/**
	 * Creates a new {@link HashMap} containing all info, if applicable. May
	 * also return null.
	 *
	 * @param input
	 * @return A new Map containing info or null.
	 */
	public HashMap<String, String> makeRequestInfo(T input);

}
