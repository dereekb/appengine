package com.dereekb.gae.client.api.service.response;

import java.util.Map;

import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Interface for accessing marshalled JSON data.
 * 
 * @author dereekb
 * 
 * @see ApiResponse
 */
public interface ClientApiResponseAccessor {

	/**
	 * Returns the success value in the client response.
	 * 
	 * @return {@code true} if success.
	 */
	public boolean getSuccess();

	/**
	 * Returns the primary data.
	 * 
	 * @return {@link ClientApiResponseData}. Never {@code null}.
	 * @throws NoClientResponseDataException
	 *             thrown if no primary data is available.
	 */
	public ClientApiResponseData getPrimaryData() throws NoClientResponseDataException;

	/**
	 * Returns a map of all included data.
	 * 
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, ClientApiResponseData> getIncludedData();

	/**
	 * Returns any errors associated with the response.
	 * 
	 * @return {@link ClientResponseError}, or {@code null} if no errors.
	 */
	public ClientResponseError getError();

}
