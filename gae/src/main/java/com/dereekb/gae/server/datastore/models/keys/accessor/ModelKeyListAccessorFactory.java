package com.dereekb.gae.server.datastore.models.keys.accessor;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


public interface ModelKeyListAccessorFactory<T extends UniqueModel> {

	public ModelKeyListAccessor<T> createAccessor();

	public ModelKeyListAccessor<T> createAccessor(Collection<ModelKey> keys);

}
