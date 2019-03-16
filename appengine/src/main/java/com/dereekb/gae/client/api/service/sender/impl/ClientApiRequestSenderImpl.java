package com.dereekb.gae.client.api.service.sender.impl;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.builder.ClientApiResponseBuilder;
import com.dereekb.gae.client.api.service.response.builder.impl.ClientApiResponseBuilderImpl;
import com.dereekb.gae.client.api.service.sender.ClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;

/**
 * {@link ClientApiRequestSender} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientApiRequestSenderImpl
        implements ClientApiRequestSender {

	private ClientRequestSender sender;
	private ClientApiResponseBuilder builder;

	public ClientApiRequestSenderImpl(ClientRequestSender sender) {
		this(sender, ClientApiResponseBuilderImpl.SINGLETON);
	}

	public ClientApiRequestSenderImpl(ClientRequestSender sender, ClientApiResponseBuilder builder) {
		this.setSender(sender);
		this.setBuilder(builder);
	}

	public ClientRequestSender getSender() {
		return this.sender;
	}

	public void setSender(ClientRequestSender sender) {
		if (sender == null) {
			throw new IllegalArgumentException("sender cannot be null.");
		}

		this.sender = sender;
	}

	public ClientApiResponseBuilder getBuilder() {
		return this.builder;
	}

	public void setBuilder(ClientApiResponseBuilder builder) {
		if (builder == null) {
			throw new IllegalArgumentException("builder cannot be null.");
		}

		this.builder = builder;
	}

	// MARK: ClientApiRequestSender
	@Override
	public ClientApiResponse sendRequest(ClientRequest request)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientRequestFailureException {
		ClientResponse response = this.sender.sendRequest(request);
		return this.builder.buildApiResponse(response);
	}

	@Override
	public String toString() {
		return "ClientApiRequestSenderImpl [sender=" + this.sender + ", builder=" + this.builder + "]";
	}

}
