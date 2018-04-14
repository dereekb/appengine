package com.dereekb.gae.client.api.auth.token.exception;

import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;

/**
 * Thrown if the token had an invalid signature.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenInvalidSignatureException extends ClientLoginTokenValidationException {

	private static final long serialVersionUID = 1L;

	public ClientLoginTokenInvalidSignatureException(ClientResponseErrorInfo error) {
		super(error);
	}

}
