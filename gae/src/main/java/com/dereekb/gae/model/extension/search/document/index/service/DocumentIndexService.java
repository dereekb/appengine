package com.dereekb.gae.model.extension.search.document.index.service;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Service used to index models into a search database.
 *
 * Changes are atomic, meaning all elements are changed, or none are changed.
 *
 * @author dereekb
 */
public interface DocumentIndexService<T extends UniqueSearchModel> {

	/**
	 * Modifies the search index using the input models.
	 *
	 * @param models
	 *            Models to index or unindex.
	 * @param action
	 *            How to change the search index.
	 * @return True if all models were changed successfully.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects requested can be changed.
	 */
	public boolean indexChange(Iterable<T> models,
	                           IndexAction action) throws AtomicOperationException;

}
