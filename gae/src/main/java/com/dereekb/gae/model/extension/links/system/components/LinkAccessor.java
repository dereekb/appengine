package com.dereekb.gae.model.extension.links.system.components;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Accessor for a {@link Link}.
 * 
 * @author dereekb
 *
 */
public interface LinkAccessor {

	/**
	 * Returns the set of all linked keys.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getLinkedModelKeys();

	/**
	 * Returns the dynamic information for this link.
	 * 
	 * @return {@link DynamicLinkAccessorInfo}. Never {@code null}.
	 */
	public DynamicLinkAccessorInfo getDynamicLinkAccessorInfo();

}
