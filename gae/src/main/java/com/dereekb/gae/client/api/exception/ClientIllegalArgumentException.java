package com.dereekb.gae.client.api.exception;

import java.util.Map;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;

/**
 * {@link ClientRequestFailureException} thrown when a client request fails due
 * to the request containing illegal arguments.
 * 
 * @author dereekb
 * 
 * @see {@link ApiIllegalArgumentException}.
 */
public class ClientIllegalArgumentException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	public ClientIllegalArgumentException() {}

	public ClientIllegalArgumentException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientIllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientIllegalArgumentException(String message) {
		super(message);
	}

	public ClientIllegalArgumentException(Throwable cause) {
		super(cause);
	}

	public ClientIllegalArgumentException(ClientResponse response) {
		super(response);
	}

	// MARK: Utility
	/**
	 * Asserts that the request was successful and there are no illegal argument
	 * errors.
	 * 
	 * @param clientResponse
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @throws ClientIllegalArgumentException
	 *             asserted exception.
	 */
	public static void assertNoIllegalArgumentException(ClientApiResponse clientResponse)
	        throws ClientIllegalArgumentException {
		ClientResponseError error = clientResponse.getError();

		if (error.getErrorType() == ClientApiResponseErrorType.BAD_REQUEST_ERROR) {
			ClientResponseErrorInfo errorInfo = tryGetIllegalArgumentApiError(error);

			if (errorInfo != null) {
				String message = errorInfo.getErrorDetail();
				throw new ClientIllegalArgumentException(message);
			}
		}
	}

	public static ClientResponseErrorInfo tryGetIllegalArgumentApiError(ClientResponseError error) {
		Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
		ClientResponseErrorInfo errorInfo = errorInfoMap.get(ApiIllegalArgumentException.ERROR_CODE);
		return errorInfo;
	}

}
