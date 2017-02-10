package com.dereekb.gae.client.api.service.response.error;

import java.util.List;

import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.utilities.web.error.ErrorInfo;

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
	 * @return {@link ClientResponseErrorType}. Never {@code null}.
	 */
	public ClientResponseErrorType getErrorType();

	/**
	 * Returns a list of error info.
	 * 
	 * @return {@link List}. Never {@code null}, but may be empty.
	 */
	public List<ErrorInfo> getErrorInfo();

}
