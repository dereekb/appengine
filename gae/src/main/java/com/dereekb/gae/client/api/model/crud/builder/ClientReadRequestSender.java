package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used for building a {@link ExecutableClientRequest} that
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientReadRequestSender<T extends UniqueModel>
        extends SecuredClientModelRequestSender<ReadRequest, ReadResponse<T>> {

}
