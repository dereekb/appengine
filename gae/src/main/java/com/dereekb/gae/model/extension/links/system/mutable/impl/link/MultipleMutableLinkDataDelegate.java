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

	public Set<ModelKey> readLinkedModelKeys(T model);

	public void setLinkedModelKeys(T model,
	                               Set<ModelKey> keys);

}
