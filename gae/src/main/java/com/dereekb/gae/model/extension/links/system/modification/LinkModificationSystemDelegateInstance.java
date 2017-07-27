package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.UndoChangesAlreadyExecutedException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * {@link LinkModificationSystemChangeInstance} for
 * {@link LinkModificationSystemDelegate}.
 * 
 * @author dereekb
 * 
 * @deprecated Instances like this are no longer necessary.
 */
@Deprecated
public interface LinkModificationSystemDelegateInstance
        extends LinkModificationSystemChangeInstance {

	/**
	 * Performs changes for the input type.
	 * 
	 * @param type
	 *            {@link String}. Never {@code null}.
	 * @param keyedMap
	 *            {@link HashMapWithList}. Never {@code null}.
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 * 
	 * @throws UndoChangesAlreadyExecutedException thrown if {@link #undoChanges()} has already been called.
	 * @throws UnavailableLinkModelException thrown if the input type is unavailable.
	 */
	public LinkModificationResultSet performModificationsForType(String type,
	                                                             HashMapWithList<ModelKey, LinkModificationPair> keyedMap, boolean atomic) throws UndoChangesAlreadyExecutedException, UnavailableLinkModelException;

}
