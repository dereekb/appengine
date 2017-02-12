package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.model.crud.services.ClientUpdateService;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ClientUpdateService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientUpdateRequestSender<T extends UniqueModel>
        extends ClientUpdateService<T>, SecuredClientModelRequestSender<UpdateRequest<T>, UpdateResponse<T>> {

}
