package com.dereekb.gae.client.api.service.response;

import java.util.Map;

import com.dereekb.gae.client.api.service.response.data.ClientResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Client API response.
 * 
 * @author dereekb
 *
 * @see ApiResponse
 */
public interface ClientApiResponse
        extends ClientResponse {

	/**
	 * Returns the primary data.
	 * 
	 * @return {@link ClientResponseData}. Never {@code null}.
	 * @throws NoClientResponseDataException
	 *             thrown if no data is available.
	 */
	public ClientResponseData getPrimaryData() throws NoClientResponseDataException;

	/**
	 * Returns a map of all included data.
	 * 
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, ClientResponseData> getIncludedData();

	/**
	 * Returns any errors associated with the response.
	 * 
	 * @return {@link ClientResponseError}, or {@code null} if no errors.
	 */
	public ClientResponseError getError();

}
