package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
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
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 * 
	 * @throws UndoChangesAlreadyExecutedException if {@link #undoChanges()} has already been called.
	 * @throws UnavailableModelException if a model within the modification map does not exist.
	 */
	public LinkModificationResultSet performModifications(HashMapWithList<ModelKey, LinkModification> keyedMap) throws UndoChangesAlreadyExecutedException, UnavailableModelException;

}
