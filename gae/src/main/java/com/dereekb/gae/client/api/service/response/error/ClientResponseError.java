package com.dereekb.gae.client.api.service.response.error;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.service.response.ClientResponse;

/**
 * Error for a {@link ClientResponse}.
 * 
 * @author dereekb
 *
 */
public interface ClientResponseError {

	/**
	 * Returns the primary error type.
	 * 
	 * @return {@link ClientApiResponseErrorType}. Never {@code null}.
	 */
	public ClientApiResponseErrorType getErrorType();

	/**
	 * Returns a list of error info.
	 * 
	 * @return {@link List}. Never {@code null}, but may be empty.
	 */
	public List<ClientResponseErrorInfo> getErrorInfo();

	/**
	 * Returns a map of errors.
	 * 
	 * @return {@link Map}. Never {@code null}, but may be empty.
	 */
	public Map<String, ClientResponseErrorInfo> getErrorInfoMap();

}
