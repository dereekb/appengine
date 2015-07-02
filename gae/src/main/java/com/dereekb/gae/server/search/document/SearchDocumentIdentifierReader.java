package com.dereekb.gae.server.search.document;

import java.util.List;

import com.google.appengine.api.search.Document;

/**
 * Reads the identifier from a scored document for model of type <T>.
 * 
 * @author dereekb
 * 
 * @param <T>
 * @param <K>
 */
public interface SearchDocumentIdentifierReader<K> {

	/**
	 * Reads a model identifier from the scored document.
	 * 
	 * @param document
	 * @return Model Identifier
	 */
	public K readIdentifier(Document document);

	/**
	 * Reads a set of scored documents and returns the model identifiers on each.
	 * 
	 * @param results
	 * @return List of model identifiers
	 */
	public List<K> readIdentifiers(Iterable<? extends Document> results);

}
