package com.dereekb.gae.model.extension.links.system.mutable;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by {@link MutableLink} for providing results about any link changes.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkChangeResult {

	/**
	 * Returns {@link ModelKey} values for all successful/non-redundant changes.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getModified();

	/**
	 * Returns {@link ModelKey} values for all redundant changes.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getRedundant();

}
