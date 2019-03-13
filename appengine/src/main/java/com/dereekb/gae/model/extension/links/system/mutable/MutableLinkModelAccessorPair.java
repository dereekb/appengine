package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Pair used by {@link MutableLinkModelAccessor} that pairs a model with it's
 * {@link MutableLinkModel}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model
 */
public interface MutableLinkModelAccessorPair<T extends UniqueModel>
        extends AlwaysKeyed<ModelKey> {

	/**
	 * Returns the model.
	 * 
	 * @return Model. Never {@code null}.
	 */
	public T getModel();

	/**
	 * 
	 * @return {@link MutableLinkModel}. Never {@code null}.
	 */
	public MutableLinkModel getMutableLinkModel();

}
