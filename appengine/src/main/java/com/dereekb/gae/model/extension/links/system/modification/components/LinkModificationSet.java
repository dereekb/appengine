package com.dereekb.gae.model.extension.links.system.modification.components;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Set of changes for a single model.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSet
        extends AlwaysKeyed<ModelKey> {

	/**
	 * Returns the list of all modifications for this type.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<LinkModification> getLinkModifications();

}
