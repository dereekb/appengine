package com.dereekb.gae.model.extension.links.system.mutable;

import java.util.List;

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
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> getKeys();

}
