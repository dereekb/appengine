package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.exception.UndoChangesAlreadyExecutedException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * {@link LinkModificationSystemChangeInstance} for
 * {@link LinkModificationSystemEntry}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemEntryInstance
        extends LinkModificationSystemChangeInstance {

	/**
	 * Performs changes for the input models.
	 * 
	 * @param keyedMap
	 *            {@link HashMapWithList}. Never {@code null}.
	 * @param atomic Boolean. {@code true} if should perform changes atomically.
	 * 
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 * 
	 * @throws UndoChangesAlreadyExecutedException if {@link #undoChanges()} has already been called.
	 * @throws AtomicOperationException if a model cannot be loaded/modified.
	 */
	public LinkModificationResultSet performModifications(HashMapWithList<ModelKey, LinkModificationPair> keyedMap, boolean atomic) throws UndoChangesAlreadyExecutedException, AtomicOperationException;

}
