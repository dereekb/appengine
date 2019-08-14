package com.dereekb.gae.model.extension.search.document.components;

import java.util.Map;

/**
 * Factory for {@link ModelSearch} values.
 *
 * @author dereekb
 *
 * @param <S>
 *            search type
 */
public interface ModelSearchFactory<S extends ModelSearch> {

	/**
	 * Creates a new search
	 *
	 * @param parameters
	 *            {@link Map} of parameters. Never {@code null}.
	 * @return Instance of search. Never {@code null}.
	 */
	public S makeSearch(Map<String, String> parameters) throws IllegalArgumentException;

}
