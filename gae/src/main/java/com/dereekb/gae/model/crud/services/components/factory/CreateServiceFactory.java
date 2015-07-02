package com.dereekb.gae.model.crud.services.components.factory;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

public interface CreateServiceFactory<T extends UniqueModel> {

	public CreateService<T> makeCreateService();

}
