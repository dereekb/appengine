package com.dereekb.gae.model.extension.search.document.index.service;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.service.exception.UnregisteredSearchTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Type of {@link DocumentIndexService} that can modify the search index with
 * {@link ModelKey} values and a type value.
 *
 * @author dereekb
 * @see {@link KeyedDocumentIndexService}
 */
public interface TypedDocumentIndexService {

	/**
	 * Whether or not the index can be changed by the specified type.
	 *
	 * @param modelType
	 * @return {@code true} if the {@link IndexAction} can be performed on the
	 *         specified type.
	 */
	public boolean canChangeIndexForType(String modelType,
	                                     IndexAction action);

	/**
	 * Modifies the search index for the given type using the input keys.
	 *
	 * @param modelType
	 *            The model type to modify.
	 * @param keys
	 *            {@link ModelKey} values that identify the models to be indexed
	 *            or unindexed.
	 * @param action
	 *            How to change the search index.
	 * @throws UnregisteredSearchTypeException
	 *             When the requested type is not available to this
	 *             indexService.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects requested cannot be changed.
	 */
	public void changeIndexWithKeys(String modelType,
	                                   Collection<ModelKey> keys,
	                                   IndexAction action)
	        throws UnregisteredSearchTypeException,
	            AtomicOperationException;

}
