package com.dereekb.gae.client.api.model.crud;

import com.dereekb.gae.client.api.model.crud.components.ClientCreateService;
import com.dereekb.gae.client.api.model.crud.components.ClientDeleteService;
import com.dereekb.gae.client.api.model.crud.components.ClientUpdateService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Edit-only model service.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientEditService<T extends UniqueModel>
        extends ClientCreateService<T>, ClientUpdateService<T>, ClientDeleteService<T> {

}