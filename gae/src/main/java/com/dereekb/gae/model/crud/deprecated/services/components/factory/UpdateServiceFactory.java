package com.dereekb.gae.model.crud.deprecated.services.components.factory;

import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

@Deprecated
public interface UpdateServiceFactory<T extends UniqueModel> {

	public UpdateService<T> makeUpdateService();

}
