package com.dereekb.gae.client.api.service.sender.impl;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.builder.ClientApiResponseBuilder;
import com.dereekb.gae.client.api.service.response.builder.impl.ClientApiResponseBuilderImpl;
import com.dereekb.gae.client.api.service.sender.ClientApiRequestSender;

/**
 * 
 * @author dereekb
 *
 */
public class ClientApiRequestSenderImpl
        implements ClientApiRequestSender {

	private ClientApiResponseBuilder builder = new ClientApiResponseBuilderImpl();

	public ClientApiRequestSenderImpl() {}

	@Override
	public ClientApiResponse sendRequest(ClientRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
