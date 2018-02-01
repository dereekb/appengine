package com.dereekb.gae.client.api.service.sender.impl;

import org.springframework.web.client.RestTemplate;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link ClientRequestSender} implementation using Spring {@link RestTemplate}.
 *
 * @author dereekb
 *
 */
public class ClientRequestSenderImpl
        implements ClientRequestSender {

	@Override
	public ClientResponse sendRequest(ClientRequest request)
	        throws ClientConnectionException,
	            ClientRequestFailureException {

		ClientRequestMethod method = request.getMethod();
		Parameters headers = request.getHeaders();
		Parameters parameters = request.getParameters();
		ClientRequestData data = request.getData();






		// TODO Auto-generated method stub
		return null;
	}

}
