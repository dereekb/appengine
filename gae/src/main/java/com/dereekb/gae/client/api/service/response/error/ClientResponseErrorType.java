package com.dereekb.gae.client.api.service.response.error;

public enum ClientResponseErrorType {

	/**
	 * The request failed due to authentication reasons.
	 */
	AUTHENTICATION_ERROR,

	/**
	 * The request was malformed and/or had an illegal argument.
	 */
	BAD_REQUEST_ERROR,

	/**
	 * Connection could not be made to the remote server.
	 */
	CONNECTION_ERROR,

	/**
	 * Server encountered an error.
	 */
	SERVER_ERROR

}
