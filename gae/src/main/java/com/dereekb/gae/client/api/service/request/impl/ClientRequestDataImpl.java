package com.dereekb.gae.client.api.service.request.impl;

import java.io.IOException;

import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.utilities.misc.parameters.impl.EncodedDataImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientRequestData} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientRequestDataImpl extends EncodedDataImpl
        implements ClientRequestData {

	public ClientRequestDataImpl(String dataString) throws IllegalArgumentException {
		super(dataString);
	}

	public static ClientRequestDataImpl make(ObjectMapper objectMapper,
	                                         Object data)
	        throws RuntimeException {
		try {
			String json = objectMapper.writeValueAsString(data);
			return new ClientRequestDataImpl(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
