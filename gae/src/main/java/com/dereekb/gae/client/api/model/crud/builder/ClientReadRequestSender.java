package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.model.crud.services.ClientReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ClientReadService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientReadRequestSender<T extends UniqueModel>
        extends ClientReadService<T>, SecuredClientModelRequestSender<ReadRequest, SimpleReadResponse<T>> {

}
