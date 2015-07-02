package com.dereekb.gae.server.search;

import com.google.appengine.api.search.Document;

/**
 * Interface used by {@link DocumentChangeSet} and
 * {@link DocumentSearchController}. Represents a single model being saved.
 *
 * @author dereekb
 */
public interface DocumentChangeModel {

	/**
	 * @return Returns the search document for this model that will be indexed.
	 */
	public Document getDocument();

	/**
	 * Called when the document is saved.
	 *
	 * @param identifier
	 *            Search API Document identifier generated.
	 */
	public void savedWithId(String identifier);

}
