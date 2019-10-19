package com.dereekb.gae.client.api.model.extension.search.document.builder;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.extension.search.document.response.ClientTypedModelSearchResponse;
import com.dereekb.gae.client.api.model.extension.search.document.response.SerializedClientTypedModelSearchApiResponse;
import com.dereekb.gae.client.api.model.extension.search.document.services.ClientTypedModelSearchService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceRequest;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ClientTypedModelSearchService} and
 * {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientTypedModelSearchRequestSender<T extends UniqueModel>
        extends ClientTypedModelSearchService<T>,
        SecuredClientModelRequestSender<TypedModelSearchServiceRequest, ClientTypedModelSearchResponse<T>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SerializedClientTypedModelSearchApiResponse<T> sendRequest(TypedModelSearchServiceRequest request,
	                                                                  ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientTooMuchInputException,
	            ClientRequestFailureException;

}
