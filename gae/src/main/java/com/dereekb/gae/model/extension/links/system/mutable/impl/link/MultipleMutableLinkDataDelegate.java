package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link MultipleMutableLinkData} delegate.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface MultipleMutableLinkDataDelegate<T> {

	/**
	 * Reads the set of linked model keys.
	 * 
	 * @param model Model. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> readLinkedModelKeys(T model);

	/**
	 * Sets the linked model keys.
	 * 
	 * @param model Model. Never {@code null}.
	 * @param keys {@link Set}. Never {@code null}.
	 */
	public void setLinkedModelKeys(T model,
	                               Set<ModelKey> keys);

}
