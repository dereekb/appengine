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

	/**
	 * Builds an accessor with a default status code, generally 200 (Success).
	 * 
	 * @param responseData
	 *            {@link String}. Never {@code null}.
	 * @return {@link ClientApiResponseAccessor}. Never {@code null}.
	 * @throws NotClientApiResponseException
	 *             thrown if an error occurs marshalling the input.
	 */
	public ClientApiResponseAccessor buildAccessor(String responseData) throws NotClientApiResponseException;

	/**
	 * Builds an accessor with a default status code, generally 200 (Success).
	 * 
	 * @param responseData
	 *            {@link String}. Never {@code null}.
	 * @param statusCode
	 *            HTTP status code.
	 * @return {@link ClientApiResponseAccessor}. Never {@code null}.
	 * @throws NotClientApiResponseException
	 *             thrown if an error occurs marshalling the input.
	 */
	public ClientApiResponseAccessor buildAccessor(String responseData,
	                                               int statusCode)
	        throws NotClientApiResponseException;

	/**
	 * Builds an accessor with a client response.
	 * 
	 * @param response
	 *            {@link ClientResponse}. Never {@code null}.
	 * @return {@link ClientApiResponseAccessor}. Never {@code null}.
	 * @throws NotClientApiResponseException
	 *             thrown if an error occurs marshalling the input.
	 */
	public ClientApiResponseAccessor buildAccessor(ClientResponse response) throws NotClientApiResponseException;

}
