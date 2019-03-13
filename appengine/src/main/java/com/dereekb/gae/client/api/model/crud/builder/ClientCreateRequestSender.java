package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientCreateApiResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientCreateService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SerializedClientCreateApiResponse<T> sendRequest(CreateRequest<T> request,
	                                                        ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientTooMuchInputException,
	            ClientRequestFailureException;

}
