package com.dereekb.gae.utilities.collections.map;

import java.util.Map;

/**
 * Case insensitive container for entries keyed by string.
 * <p>
 * Generally wraps a {@link CaseInsensitiveMap}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface CaseInsensitiveEntryContainer<T> {

	/**
	 * Returns the entries map.
	 *
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, T> getEntries();

	/**
	 * Returns the entry for the type, if it exists.
	 *
	 * @param type
	 *            Type key.
	 * @return Entry. Never {@code null}.
	 * @throws RuntimeException
	 *             Thrown if the type does not exist.
	 *             The exception thrown by the container varies between
	 *             implementations.
	 */
	public T getEntryForType(String type) throws RuntimeException;

}
