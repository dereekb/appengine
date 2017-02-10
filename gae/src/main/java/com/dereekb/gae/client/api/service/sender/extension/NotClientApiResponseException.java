package com.dereekb.gae.client.api.service.sender.extension;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientResponse;

/**
 * Thrown when a {@link ClientResponse} cannot be decoded to a proper
 * {@link ClientApiResponse}.
 * 
 * @author dereekb
 *
 */
public class NotClientApiResponseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotClientApiResponseException() {
		super();
	}

	public NotClientApiResponseException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotClientApiResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotClientApiResponseException(String message) {
		super(message);
	}

	public NotClientApiResponseException(Throwable cause) {
		super(cause);
	}

}
