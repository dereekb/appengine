package com.dereekb.gae.model.extension.links.system.mutable;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by {@link MutableLink} for change requests.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkChange {

	/**
	 * Returns the type of change to perform.
	 * 
	 * @return {@link MutableLinkChangeType}. Never {@code null}.
	 */
	public MutableLinkChangeType getLinkChangeType();

	/**
	 * Returns the keys to change.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getKeys();

}
