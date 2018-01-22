package com.dereekb.gae.server.event.event;

import java.util.Set;

/**
 * Arbitrary data associated with an {@link Event}.
 *
 * @author dereekb
 *
 */
public interface EventData {

	/**
	 * Returns a set of all available property keys.
	 *
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getPropertyKeys();

	/**
	 * Returns the property value for that key.
	 *
	 * @return
	 */
	public String getProperty(String key);

}
