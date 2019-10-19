package com.dereekb.gae.client.api.model.extension.search.document.builder.impl;

import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.model.extension.search.document.builder.ClientTypedModelSearchRequestSender;
import com.dereekb.gae.client.api.model.extension.search.document.response.ClientTypedModelSearchResponse;
import com.dereekb.gae.client.api.model.extension.search.document.response.SerializedClientTypedModelSearchApiResponse;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
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
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceRequest;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceResponse;
import com.dereekb.gae.model.extension.search.document.service.impl.TypedModelSearchServiceRequestImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

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
public class ClientTypedModelSearchRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientSearchRequestSender<T, O, TypedModelSearchServiceRequest, ClientTypedModelSearchResponse<T>>
        implements ClientTypedModelSearchRequestSender<T> {

	/**
	 * GET request to {@code /<type>/search} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s/search";

	public ClientTypedModelSearchRequestSenderImpl(TypedBidirectionalConverter<T, O> typedConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(typedConverter, keyTypeConverter, requestSender);
	}

	public ClientTypedModelSearchRequestSenderImpl(TypedBidirectionalConverter<T, O> typedConverter,
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

	@Override
	public TypedModelSearchServiceResponse<T> searchModels(TypedModelSearchServiceRequest request)
	        throws IllegalQueryArgumentException {
		try {
			return this.search(request);
		} catch (ClientIllegalArgumentException | ClientKeyedInvalidAttributeException e) {
			throw new IllegalQueryArgumentException(e);
		} catch (ClientRequestFailureException e) {
			throw new RuntimeException(e);
		}
	}

	// MARK: ClientTypedModelSearchServiceRequestSender
	@Override
	public ClientTypedModelSearchResponse<T> search(TypedModelSearchServiceRequest request)
	        throws ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {
		return this.search(request, null);
	}

	@Override
	public ClientTypedModelSearchResponse<T> search(TypedModelSearchServiceRequest request,
	                                         ClientRequestSecurity security)
	        throws ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<ClientTypedModelSearchResponse<T>> clientResponse = this.sendRequest(request, security);
		this.assertSuccessfulResponse(clientResponse);
		return clientResponse.getSerializedResponse();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public SerializedClientTypedModelSearchApiResponse<T> sendRequest(TypedModelSearchServiceRequest request,
	                                                                  ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientTooMuchInputException,
	            ClientRequestFailureException {
		ClientRequest clientRequest = this.buildClientRequest(request);
		ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);
		return new SerializedClientTypedModelSearchServiceResponseImpl(request, clientResponse, security);
	}

	@Override
	public ClientRequest buildClientRequest(TypedModelSearchServiceRequest request)
	        throws ClientRequestFailureException {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.GET);

		Map<String, String> parametersMap = request.getParameters();

		ParametersImpl parameters = new ParametersImpl(parametersMap);
		parameters.addEntry("index", request.getIndex());

		clientRequest.setParameters(parameters);

		return clientRequest;
	}

	@Override
	public ClientTypedModelSearchResponse<T> serializeResponseData(TypedModelSearchServiceRequest request,
	                                                                      ClientApiResponse response)
	        throws ClientResponseSerializationException {
		throw new UnsupportedOperationException("Security is required.");
	}

	@Override
	public ClientTypedModelSearchResponse<T> serializeResponseData(TypedModelSearchServiceRequest request,
	                                                         ClientApiResponse response,
	                                                         ClientRequestSecurity security)
	        throws ClientResponseSerializationException {
		return new ClientTypedModelSearchResponseImpl(request, response, security);
	}

	protected class SerializedClientTypedModelSearchServiceResponseImpl extends SerializedClientApiResponseImpl
	        implements SerializedClientTypedModelSearchApiResponse<T> {

		public SerializedClientTypedModelSearchServiceResponseImpl(TypedModelSearchServiceRequest request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(request, response, security);
		}

	}

	private class ClientTypedModelSearchResponseImpl extends AbstractClientSearchResponse
	        implements ClientTypedModelSearchResponse<T> {

		private final TypedModelSearchServiceRequest typedRequest;

		public ClientTypedModelSearchResponseImpl(TypedModelSearchServiceRequest request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(request, response, security);
			this.typedRequest = request;
		}

		// MARK: ClientModelQueryResponse
		@Override
		public boolean hasNextSearch() {
			// Assume it has more if this response isn't empty.
			return this.hasResults() && this.getSearchCursor() != null;
		}

		@Override
		public ClientTypedModelSearchResponse<T> performNextSearch()
		        throws UnsupportedOperationException,
		            ClientKeyedInvalidAttributeException,
		            ClientIllegalArgumentException,
		            ClientRequestFailureException {

			if (this.hasNextSearch() == false) {
				throw new UnsupportedOperationException("This is the end of the search.");
			} else if (this.getSecurity() == null) {
				throw new UnsupportedOperationException("There is no security set.");
			}

			TypedModelSearchServiceRequestImpl searchRequest = new TypedModelSearchServiceRequestImpl(this.getRequest());
			searchRequest.setIndex(this.typedRequest.getIndex());
			searchRequest.setCursor(this.getSearchCursor());
			return ClientTypedModelSearchRequestSenderImpl.this.search(searchRequest, this.getSecurity());
		}

	}

	@Override
	public String toString() {
		return "ClientQueryRequestSenderImpl []";
	}

}
