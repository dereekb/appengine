package com.dereekb.gae.model.crud.deprecated.services.components.factory;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

@Deprecated
public interface ReadServiceFactory<T extends UniqueModel> {

	public ReadService<T> makeReadService();

}
