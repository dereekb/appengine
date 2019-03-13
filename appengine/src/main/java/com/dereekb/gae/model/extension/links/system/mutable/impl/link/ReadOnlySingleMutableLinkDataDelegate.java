package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link SingleMutableLinkData} delegate.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ReadOnlySingleMutableLinkDataDelegate<T> {

	/**
	 * Returns the current linked key for the specific link on the model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * 
	 * @return {@link ModelKey}. Can be {@code null} if not set.
	 */
	public ModelKey readLinkedModelKey(T model);

}
