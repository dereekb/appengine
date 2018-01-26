package com.dereekb.gae.client.api.model.extension.search.query.builder.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.client.api.model.extension.search.query.response.ClientModelQueryResponse;
import com.dereekb.gae.client.api.model.extension.search.query.response.SerializedClientModelQueryApiResponse;
import com.dereekb.gae.client.api.model.extension.search.shared.builder.impl.AbstractClientSearchRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.dereekb.gae.server.search.model.impl.SearchRequestImpl;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;
import com.googlecode.objectify.Key;

/**
 * {@link ClientQueryRequestSender} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 */
public class ClientQueryRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientSearchRequestSender<T, O, SearchRequest, ClientModelQueryResponse<T>>
        implements ClientQueryRequestSender<T> {

	/**
	 * GET request to {@code /<type>/query} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s/query";

	public ClientQueryRequestSenderImpl(TypedBidirectionalConverter<T, O> typedConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender,
	        ObjectifyKeyConverter<T, ModelKey> keyConverter) throws IllegalArgumentException {
		super(typedConverter, keyTypeConverter, requestSender, keyConverter);
	}

	// MARK: Abstract
	@Override
	protected String getDefaultPathFormat() {
		return DEFAULT_PATH_FORMAT;
	}

	// MARK: ClientQueryRequestSender
	@Override
	public ClientModelQueryResponse<T> query(SearchRequest request)
	        throws ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {
		return this.query(request, null);
	}

	@Override
	public ClientModelQueryResponse<T> query(SearchRequest request,
	                                         ClientRequestSecurity security)
	        throws ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<ClientModelQueryResponse<T>> clientResponse = this.sendRequest(request, security);
		this.assertSuccessfulResponse(clientResponse);
		return clientResponse.getSerializedResponse();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public SerializedClientModelQueryApiResponse<T> sendRequest(SearchRequest request,
	                                                            ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientTooMuchInputException,
	            ClientRequestFailureException {
		ClientRequest clientRequest = this.buildClientRequest(request);
		ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);
		return new SerializedClientModelQueryApiResponseImpl(request, clientResponse, security);
	}

	@Override
	public ClientRequest buildClientRequest(SearchRequest request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.GET);

		Map<String, String> parametersMap = request.getParameters();

		ParametersImpl parameters = new ParametersImpl(parametersMap);
		clientRequest.setParameters(parameters);

		return clientRequest;
	}

	@Override
	public ClientModelQueryResponse<T> serializeResponseData(SearchRequest request,
	                                                         ClientApiResponse response)
	        throws ClientResponseSerializationException {
		throw new UnsupportedOperationException("Security is required.");
	}

	@Override
	public ClientModelQueryResponse<T> serializeResponseData(SearchRequest request,
	                                                         ClientApiResponse response,
	                                                         ClientRequestSecurity security)
	        throws ClientResponseSerializationException {
		return new ClientQueryResponseImpl(request, response, security);
	}

	protected class SerializedClientModelQueryApiResponseImpl extends SerializedClientApiResponseImpl
	        implements SerializedClientModelQueryApiResponse<T> {

		public SerializedClientModelQueryApiResponseImpl(SearchRequest request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(request, response, security);
		}

	}

	private class ClientQueryResponseImpl extends AbstractClientSearchResponse
	        implements ClientModelQueryResponse<T> {

		private List<Key<T>> objectifyKeyResults;

		public ClientQueryResponseImpl(SearchRequest request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(request, response, security);
		}

		@Override
		public List<Key<T>> getObjectifyKeyResults() {
			if (this.objectifyKeyResults == null) {
				Collection<ModelKey> modelKeys = this.getKeyResults();
				this.objectifyKeyResults = ClientQueryRequestSenderImpl.this.getKeyConverter().convertFrom(modelKeys);
			}

			return this.objectifyKeyResults;
		}

		// MARK: ClientModelQueryResponse
		@Override
		public boolean hasNextQuery() {
			// Assume it has more if this response isn't empty.
			return this.hasResults() && this.getSearchCursor() != null;
		}

		@Override
		public ClientModelQueryResponse<T> performNextQuery()
		        throws UnsupportedOperationException,
		            ClientIllegalArgumentException,
		            ClientRequestFailureException {

			if (this.hasNextQuery() == false) {
				throw new UnsupportedOperationException("This is the last query.");
			} else if (this.getSecurity() == null) {
				throw new UnsupportedOperationException("There is no security set.");
			}

			SearchRequestImpl searchRequest = new SearchRequestImpl(this.getRequest());
			searchRequest.setCursor(this.getSearchCursor());
			return ClientQueryRequestSenderImpl.this.query(searchRequest, this.getSecurity());
		}

	}

	@Override
	public String toString() {
		return "ClientQueryRequestSenderImpl []";
	}

}
