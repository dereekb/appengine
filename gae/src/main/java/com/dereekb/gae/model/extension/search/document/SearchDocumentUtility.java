package com.dereekb.gae.model.extension.search.document;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.search.utility.ScoredDocumentKeyReader;
import com.dereekb.gae.server.search.UniqueSearchModel;
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

	public static <K> List<K> readModelKeys(ScoredDocumentKeyReader<K> reader,
	                                               List<ScoredDocument> documents) {
		List<K> keys = new ArrayList<K>();

		for (ScoredDocument document : documents) {
			K key = reader.readKeyFromDocument(document);
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
