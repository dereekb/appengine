package com.dereekb.gae.client.api.exception;

import java.util.Map;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.web.api.model.exception.ApiTooMuchInputException;

/**
 * {@link ClientRequestFailureException} thrown by failures due to
 * {@link ApiTooMuchInputException}.
 * 
 * @author dereekb
 *
 */
public class ClientTooMuchInputException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	public ClientTooMuchInputException() {}
	
	public ClientTooMuchInputException(String message) {
		super(message);
	}

	// MARK: Utility
	/**
	 * Asserts that the request was successful and there are no illegal argument
	 * errors.
	 * 
	 * @param clientResponse
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @throws ClientTooMuchInputException
	 *             asserted exception.
	 */
	public static void assertNotTooMuchInputException(ClientApiResponse clientResponse)
	        throws ClientTooMuchInputException {
		ClientResponseError error = clientResponse.getError();

		if (error.getErrorType() == ClientApiResponseErrorType.BAD_REQUEST_ERROR) {
			ClientResponseErrorInfo errorInfo = tryGetTooMuchInputApiError(error);

			if (errorInfo != null) {
				String message = errorInfo.getErrorDetail();
				throw new ClientTooMuchInputException(message);
			}
		}
	}

	public static ClientResponseErrorInfo tryGetTooMuchInputApiError(ClientResponseError error) {
		Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
		ClientResponseErrorInfo errorInfo = errorInfoMap.get(ApiTooMuchInputException.ERROR_CODE);
		return errorInfo;
	}

}
