package com.dereekb.gae.model.extension.search.document.index.service;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


/**
 * Service that allows updating search indices using {@link ModelKey} values.
 *
 * @author dereekb
 */
public interface KeyedDocumentIndexService {

	/**
	 *
	 * @param action
	 *            {@link IndexAction}. Never {@code null}.
	 * @return {@code true} if this indexService can perform the {@link IndexAction}.
	 */
	public boolean canPerformAction(IndexAction action);

	/**
	 * Modifies the search index using the input keys.
	 *
	 * @param keys
	 *            {@link ModelKey} values that identify the models to be indexed
	 *            or unindexed.
	 * @param action
	 *            How to change the search index.
	 * @return True if all models were changed successfully.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects requested can be changed.
	 */
	public boolean indexChangeWithKeys(Collection<ModelKey> keys,
	                           IndexAction action) throws AtomicOperationException;

}
