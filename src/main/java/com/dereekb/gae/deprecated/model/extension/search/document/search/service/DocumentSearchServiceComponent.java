package com.thevisitcompany.gae.deprecated.model.extension.search.document.search.service;

import java.util.Collections;
import java.util.List;

import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.DocumentSearchRequest;
import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.functions.DocumentSearchFunction;
import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.functions.DocumentSearchPair;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedSearchableModel;

@Deprecated
public class DocumentSearchServiceComponent<T extends KeyedSearchableModel<K>, K>
        implements DocumentSearchService<T, K> {

	private final DocumentSearchFunction<T, K> documentSearchFunction;

	public DocumentSearchServiceComponent(DocumentSearchFunction<T, K> documentSearchFunction) {
		super();
		this.documentSearchFunction = documentSearchFunction;
	}

	@Override
	public List<K> search(DocumentSearchRequest search) {
		DocumentSearchPair<T, K> searchPair = new DocumentSearchPair<T, K>(search);
		searchPair.setReferenceOnlySearch(true);

		documentSearchFunction.addObject(searchPair);
		boolean success = documentSearchFunction.run();

		List<K> results = null;

		if (success) {
			results = searchPair.getIdentifierResults();
		} else {
			results = Collections.emptyList();
		}

		return results;
	}

	@Override
	public List<T> searchModels(DocumentSearchRequest search) {
		DocumentSearchPair<T, K> searchPair = new DocumentSearchPair<T, K>(search);
		searchPair.setReferenceOnlySearch(false);

		documentSearchFunction.addObject(searchPair);
		boolean success = documentSearchFunction.run();

		List<T> results = null;

		if (success) {
			results = searchPair.getResults();
		} else {
			results = Collections.emptyList();
		}

		return results;
	}
}
