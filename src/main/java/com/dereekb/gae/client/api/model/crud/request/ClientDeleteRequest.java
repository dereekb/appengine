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

	/**
	 * Whether or not models should be returned. Is more for performance issues.
	 * 
	 * @return {@code true} if models should be returned.
	 */
	public boolean shouldReturnModels();

}
