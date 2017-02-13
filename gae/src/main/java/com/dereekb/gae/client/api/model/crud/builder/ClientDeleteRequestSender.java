package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.model.crud.request.ClientDeleteRequest;
import com.dereekb.gae.client.api.model.crud.response.ClientDeleteResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientDeleteService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ClientDeleteService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientDeleteRequestSender<T extends UniqueModel>
        extends ClientDeleteService<T>, SecuredClientModelRequestSender<ClientDeleteRequest, ClientDeleteResponse<T>> {

}
