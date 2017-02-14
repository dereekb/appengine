package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.model.crud.services.ClientCreateService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ClientCreateService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientCreateRequestSender<T extends UniqueModel>
        extends ClientCreateService<T>, SecuredClientModelRequestSender<CreateRequest<T>, CreateResponse<T>> {

}
