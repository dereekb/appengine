package com.dereekb.gae.client.api.model.crud;

import com.dereekb.gae.client.api.model.crud.components.ClientReadService;
import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Client CRUD service.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @see CrudService
 */
public interface ClientCrudService<T extends UniqueModel>
        extends ClientReadService<T>, ClientEditService<T> {

}
