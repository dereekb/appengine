package com.dereekb.gae.client.api.auth.token.exception;

import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;

/**
 * {@link ClientRequestFailureException} equivalent of {@link TokenException}.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenValidationException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private ClientResponseErrorInfo error;

	public ClientLoginTokenValidationException(ClientResponseErrorInfo error) {
		this.setError(error);
	}

	public ClientResponseErrorInfo getError() {
		return this.error;
	}

	public void setError(ClientResponseErrorInfo error) {
		this.error = error;
	}

	// MARK: Utility
	public static void assertNoTokenValidationException(ClientApiResponse clientResponse)
	        throws ClientLoginTokenExpiredException,
	            ClientLoginTokenInvalidException,
	            ClientLoginTokenInvalidSignatureException,
	            ClientLoginTokenValidationException {
		ClientResponseError error = clientResponse.getError();

		if (error.getErrorType() == ClientApiResponseErrorType.AUTHENTICATION_ERROR) {
			Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();

			if (errorInfoMap.containsKey("EXPIRED_AUTHORIZATION")) {
				throw new ClientLoginTokenExpiredException(errorInfoMap.get("EXPIRED_AUTHORIZATION"));
			} else if (errorInfoMap.containsKey("INVALID_AUTHORIZATION")) {
				throw new ClientLoginTokenInvalidException(errorInfoMap.get("INVALID_AUTHORIZATION"));
			} else if (errorInfoMap.containsKey("INVALID_SIGNATURE")) {
				throw new ClientLoginTokenInvalidSignatureException(errorInfoMap.get("INVALID_SIGNATURE"));
			}

			// TODO: Throw other exception maybe?
		}
	}

}
