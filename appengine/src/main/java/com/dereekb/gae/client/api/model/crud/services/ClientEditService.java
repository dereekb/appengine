package com.dereekb.gae.client.api.model.crud.services;

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