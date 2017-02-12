package com.dereekb.gae.client.api.service.response.error;

/**
 * High-level response errors that encapsulate/represent multiple different HTTP
 * response codes.
 * 
 * @author dereekb
 *
 */
public enum ClientApiResponseErrorType {

	/**
	 * No error was encountered.
	 */
	NO_ERROR,

	/**
	 * The request failed due to authentication reasons.
	 */
	AUTHENTICATION_ERROR,

	/**
	 * The request was malformed and/or had an illegal argument.
	 */
	BAD_REQUEST_ERROR,

	/**
	 * The request was not malformed, but another error occured while
	 * processing.
	 */
	OTHER_BAD_RESPONSE_ERROR,

	/**
	 * Connection could not be made to the remote server.
	 * 
	 * @deprecated Connection errors are not proper response errors and have no
	 *             HTTP status code associated with them.
	 */
	@Deprecated
	CONNECTION_ERROR,

	/**
	 * Server encountered an error.
	 */
	SERVER_ERROR;

	public static ClientApiResponseErrorType typeForCode(int statusCode) {
		ClientApiResponseErrorType errorType = null;

		if (statusCode >= 500) {
			errorType = ClientApiResponseErrorType.SERVER_ERROR;
		} else if (statusCode >= 400) {
			if (statusCode == 401 || statusCode == 403) {
				errorType = ClientApiResponseErrorType.AUTHENTICATION_ERROR;
			} else if (statusCode == 400) {
				errorType = ClientApiResponseErrorType.BAD_REQUEST_ERROR;
			} else {
				errorType = ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR;
			}
		} else if (statusCode >= 200) {
			errorType = ClientApiResponseErrorType.NO_ERROR;
		} else {
			errorType = ClientApiResponseErrorType.CONNECTION_ERROR;
		}

		return errorType;
	}

}
