package com.dereekb.gae.client.api.model.extension.search.query.builder;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.extension.search.query.response.ClientModelQueryResponse;
import com.dereekb.gae.client.api.model.extension.search.query.response.SerializedClientModelQueryApiResponse;
import com.dereekb.gae.client.api.model.extension.search.query.services.ClientQueryService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;

/**
 * {@link ClientQueryService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientQueryRequestSender<T extends UniqueModel>
        extends ClientQueryService<T>, SecuredClientModelRequestSender<SearchRequest, ClientModelQueryResponse<T>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SerializedClientModelQueryApiResponse<T> sendRequest(SearchRequest request,
	                                                            ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientTooMuchInputException,
	            ClientRequestFailureException;

}
