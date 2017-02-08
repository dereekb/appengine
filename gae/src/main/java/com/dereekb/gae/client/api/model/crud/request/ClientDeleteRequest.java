package com.dereekb.gae.client.api.model.crud.request;

import com.dereekb.gae.model.crud.services.request.DeleteRequest;

/**
 * {@link DeleteRequest} extension for {@link CreateDeleteService}.
 * 
 * @author dereekb
 *
 */
public interface ClientDeleteRequest
        extends DeleteRequest {

	public boolean shouldReturnModels();

}
