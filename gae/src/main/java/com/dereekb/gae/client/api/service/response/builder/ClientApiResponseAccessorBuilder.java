package com.dereekb.gae.client.api.service.response.builder;

import com.dereekb.gae.client.api.service.response.ClientApiResponseAccessor;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;

/**
 * Used for building a {@link ClientApiResponseAccessor}.
 * 
 * @author dereekb
 *
 */
public interface ClientApiResponseAccessorBuilder {

	public ClientApiResponseAccessor buildAccessor(String responseData) throws NotClientApiResponseException;

	public ClientApiResponseAccessor buildAccessor(String responseData,
	                                               int statusCode)
	        throws NotClientApiResponseException;

	public ClientApiResponseAccessor buildAccessor(ClientResponse response) throws NotClientApiResponseException;

}
