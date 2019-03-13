package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;

/**
 * Abstract interface for objects that contain a {@link LinkModification}.
 * 
 * @author dereekb
 *
 */
public abstract interface LinkModificationReference {

	/**
	 * Returns the original modification request.
	 * 
	 * @return {@link LinkModification}. Never {@code null}.
	 */
	public LinkModification getLinkModification();
	
}
