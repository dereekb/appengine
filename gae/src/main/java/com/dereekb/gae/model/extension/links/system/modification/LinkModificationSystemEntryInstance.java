package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
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
	 */
	public LinkModificationResultSet performModifications(HashMapWithList<ModelKey, LinkModification> keyedMap);

}
