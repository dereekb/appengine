package com.dereekb.gae.client.api.auth.token.exception;

import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;

/**
 * Thrown if the token is invalid.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenInvalidException extends ClientLoginTokenValidationException {

	private static final long serialVersionUID = 1L;

	public ClientLoginTokenInvalidException(ClientResponseErrorInfo error) {
		super(error);
	}

}
