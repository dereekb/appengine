package com.thevisitcompany.gae.deprecated.model.extension.search.document.search.service;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.DocumentSearchRequest;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedSearchableModel;

@Deprecated
public interface DocumentSearchService<T extends KeyedSearchableModel<K>, K> {

	/**
	 * Searching through the Search API, which allows for more comprehensive searches.
	 *
	 * @param search
	 * @return List of identifiers that correspond to models of type <T> from the search.
	 */
	public abstract List<K> search(DocumentSearchRequest search);

	/**
	 * Searching through the Search API, which allows for more comprehensive searches.
	 *
	 * @param search
	 * @return List of models retrieved from the search.
	 */
	public abstract List<T> searchModels(DocumentSearchRequest search);

}
