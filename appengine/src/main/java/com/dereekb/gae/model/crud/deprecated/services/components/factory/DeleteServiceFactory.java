package com.dereekb.gae.model.crud.deprecated.services.components.factory;

import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

@Deprecated
public interface DeleteServiceFactory<T extends UniqueModel> {

	public DeleteService<T> makeDeleteService();

}
