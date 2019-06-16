package com.dereekb.gae.model.extension.search.document.search.utility;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.deprecated.search.UniqueSearchModel;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Utility type used by elements in the Search extension.
 *
 * @author dereekb
 */
public final class SearchDocumentUtility {

	public static <T extends UniqueSearchModel> List<T> filterDocumentsWithSearchIndexes(Iterable<T> searchables) {
		List<T> filteredDocuments = new ArrayList<T>();

		for (T searchable : searchables) {
			boolean hasIdentifier = (searchable.getSearchIdentifier() != null);

			if (hasIdentifier) {
				filteredDocuments.add(searchable);
			}
		}

		return filteredDocuments;
	}

	public static List<ModelKey> readModelKeys(ScoredDocumentKeyReader reader,
	                                           Iterable<ScoredDocument> documents) {
		List<ModelKey> keys = new ArrayList<ModelKey>();

		for (ScoredDocument document : documents) {
			ModelKey key = reader.keyFromDocument(document);
			keys.add(key);
		}

		return keys;
	}

	public static <T extends UniqueSearchModel> List<String> getDocumentIdentifiers(Iterable<T> searchables) {
		List<String> documents = new ArrayList<String>();

		for (T searchable : searchables) {
			String document = searchable.getSearchIdentifier();

			if (document != null) {
				documents.add(document);
			}
		}

		return documents;
	}

}
