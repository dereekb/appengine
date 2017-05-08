package com.dereekb.gae.client.api.model.extension.link.impl;

import java.util.List;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequest;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequestSender;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceResponse;
import com.dereekb.gae.client.api.model.extension.link.exception.ClientLinkServiceChangeException;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractSecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientLinkServiceRequestSender} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientLinkRequestSenderImpl extends AbstractSecuredClientModelRequestSender<ClientLinkServiceRequest, ClientLinkServiceResponse>
        implements ClientLinkServiceRequestSender {

	private static final String DEFAULT_PATH_FORMAT = "/%s/link";

	private String pathFormat = DEFAULT_PATH_FORMAT;
	private TypeModelKeyConverter keyTypeConverter;

	public ClientLinkRequestSenderImpl(SecuredClientApiRequestSender requestSender,
	        TypeModelKeyConverter keyTypeConverter) throws IllegalArgumentException {
		super(requestSender);
		this.setKeyTypeConverter(keyTypeConverter);
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		if (keyTypeConverter == null) {
			throw new IllegalArgumentException("keyTypeConverter cannot be null.");
		}

		this.keyTypeConverter = keyTypeConverter;
	}

	// MARK: ClientLinkServiceRequestSender
	@Override
	public ClientLinkServiceResponse updateLinks(ClientLinkServiceRequest request)
	        throws ClientLinkServiceChangeException,
	            ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException {
		return this.updateLinks(request, null);
	}

	@Override
	public ClientLinkServiceResponse updateLinks(ClientLinkServiceRequest request,
	                                             ClientRequestSecurity security)
	        throws ClientLinkServiceChangeException,
	            ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<ClientLinkServiceResponse> clientResponse = this.sendRequest(request, security);
		this.assertSuccessfulResponse(clientResponse);
		return clientResponse.getSerializedPrimaryData();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public ClientRequest buildClientRequest(ClientLinkServiceRequest request) {
		ClientRequestUrl url = this.makeRequestUrl(request.getType());
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.PUT);

		ClientRequestData data = this.makeRequestData(request);
		clientRequest.setData(data);

		return clientRequest;
	}

	@Override
	public ClientLinkServiceResponse serializeResponseData(ClientLinkServiceRequest request,
	                                                       ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientLinkServiceResponseImpl(response);
	}

	// MARK: Utility
	public ClientRequestUrl makeRequestUrl(String type) {
		String formattedPath = String.format(this.pathFormat, type);
		return new ClientRequestUrlImpl(formattedPath);
	}

	public ClientRequestData makeRequestData(ClientLinkServiceRequest request) {

		ObjectMapper mapper = this.getObjectMapper();

		List<ApiLinkChangeImpl> data = ApiLinkChangeImpl.makeFromChanges(request.getChanges());
		ApiLinkChangeRequest changeRequest = new ApiLinkChangeRequest(data, request.isAtomic());

		ClientRequestDataImpl requestData = ClientRequestDataImpl.make(mapper, changeRequest);
		return requestData;
	}

	public void assertSuccessfulResponse(SerializedClientApiResponse<ClientLinkServiceResponse> clientResponse) {
		if (clientResponse.getSuccess() == false) {
			// TODO: Handle and throw the following:
			/*
			 * ClientLinkServiceChangeException,
			 * ClientIllegalArgumentException,
			 * ClientAtomicOperationException,
			 * ClientRequestFailureException
			 */
		}
	}

	private class ClientLinkServiceResponseImpl
	        implements ClientLinkServiceResponse {

		private ClientApiResponse response;

		public ClientLinkServiceResponseImpl(ClientApiResponse response) {
			this.response = response;
		}

		// MARK: ClientLinkServiceResponse
		@Override
		public HashMapWithSet<String, ModelKey> getMissingKeys() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
