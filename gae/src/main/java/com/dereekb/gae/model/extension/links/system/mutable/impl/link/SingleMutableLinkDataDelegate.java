package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link SingleMutableLinkData} delegate.
 * 
 * @author dereekb
 *
 * @param <T> model type
 */
public interface SingleMutableLinkDataDelegate<T> {

	public ModelKey readLinkedModelKey(T model);

	public void setLinkedModelKey(T model, ModelKey modelKey);
	
}
