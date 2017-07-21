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
 * {@link LinkModificationSystemDelegate}.
 * 
 * @author dereekb
 *
 */
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
	 */
	public LinkModificationResultSet performModificationsForType(String type,
	                                                             HashMapWithList<ModelKey, LinkModification> keyedMap) throws UndoChangesAlreadyExecutedException, UnavailableModelException;

}
