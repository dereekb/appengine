package com.dereekb.gae.model.extension.search.document.index.service;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.deprecated.search.document.index.IndexAction;
import com.dereekb.gae.server.deprecated.search.UniqueSearchModel;

/**
 * Service used to index models into a search database.
 * <p>
 * Changes are atomic, meaning all elements are changed, or none are changed.
 *
 * @author dereekb
 */
public interface DocumentIndexService<T extends UniqueSearchModel> {

	/**
	 * Modifies the search index using the input models.
	 * <p>
	 * Indexing a model may result in it's search identifier changing, but
	 * un-indexing will not result in a change.
	 *
	 * @param models
	 *            Models to change.
	 * @param action
	 *            How to change the search index.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects requested can be changed.
	 */
	public void indexChange(Iterable<T> models,
	                        IndexAction action) throws AtomicOperationException;

}
