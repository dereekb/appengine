package com.dereekb.gae.client.api.service.response;

import com.dereekb.gae.client.api.service.sender.ClientRequestSender;

/**
 * Response returned from a {@link ClientRequestSender}.
 * 
 * @author dereekb
 *
 */
public interface ClientResponse {

	/**
	 * Returns true of {@link #getStatus()} returns a 200.
	 * 
	 * @return {@code true} if successful.
	 */
	public boolean isSuccessful();

	/**
	 * Returns the HTTP status code.
	 * 
	 * @return {@code int} for the HTTP code.
	 */
	public int getStatus();

	/**
	 * Returns the response data.
	 * 
	 * @return {@link String}, or {@code null} if no data.
	 */
	public String getData();

}
