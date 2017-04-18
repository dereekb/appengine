package com.dereekb.gae.server.datastore.models.keys.accessor;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Accessor that provides access to models, their {@link ModelKey} values, and
 * the model type.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelKeyListAccessor<T extends UniqueModel> {

	public String getModelType();

	public List<ModelKey> getModelKeys();

	public List<T> getModels();

}
