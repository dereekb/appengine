package com.dereekb.gae.server.datastore.factory;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;

public interface GetterFactory<T extends UniqueModel> {

	public Getter<T> makeGetter();

}
