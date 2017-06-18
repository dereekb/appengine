package com.dereekb.gae.server.datastore.models.keys.accessor;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Factory for creating new {@link ModelKeyListAccessor} instances.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelKeyListAccessorFactory<T extends UniqueModel> {

	public ModelKeyListAccessor<T> createAccessor();

	public ModelKeyListAccessor<T> createAccessor(Collection<ModelKey> keys);

	public ModelKeyListAccessor<T> createAccessorWithModels(Collection<T> models);

}
