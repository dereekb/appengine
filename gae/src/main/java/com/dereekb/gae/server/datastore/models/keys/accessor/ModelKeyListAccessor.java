package com.dereekb.gae.server.datastore.models.keys.accessor;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


public interface ModelKeyListAccessor<T extends UniqueModel> {

	public String getModelType();

	public List<ModelKey> getModelKeys();

	public List<T> getModels();

}
