package com.dereekb.gae.model.crud.services;

import com.dereekb.gae.server.datastore.models.UniqueModel;

public interface CrudServiceFactory<T extends UniqueModel> {

	public CrudService<T> makeCrudService();

}

