package com.dereekb.gae.client.api.auth.token;

import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenExpiredException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidSignatureException;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.web.api.auth.controller.token.LoginTokenController;

/**
 * Client interface for interacting with {@link LoginTokenController}
 * validation function.
 *
 * @author dereekb
 *
 */
public interface ClientLoginTokenValidationService {

	/**
	 * Checks the validation of a token.
	 *
	 * @param request
	 *            {@link ClientLoginTokenValidationRequest}. Never
	 *            {@code null}.
	 * @return {@link ClientLoginTokenValidationResponse}. Never {@code null}.
	 *
	 * @throws ClientLoginTokenExpiredException
	 *             thrown if the token is expired.
	 * @throws ClientLoginTokenInvalidException
	 *             thrown if the token is invalid.
	 * @throws ClientLoginTokenInvalidSignatureException
	 *             thrown if the token has an invalid signature.
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientLoginTokenValidationResponse validateToken(ClientLoginTokenValidationRequest request)
	        throws ClientLoginTokenExpiredException,
	            ClientLoginTokenInvalidException,
	            ClientLoginTokenInvalidSignatureException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException;

}
