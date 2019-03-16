package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientUpdateApiResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientUpdateService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.SimpleUpdateResponse;
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
        extends ClientUpdateService<T>, SecuredClientModelRequestSender<UpdateRequest<T>, SimpleUpdateResponse<T>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SerializedClientUpdateApiResponse<T> sendRequest(UpdateRequest<T> request,
	                                                        ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientTooMuchInputException,
	            ClientRequestFailureException;

}
