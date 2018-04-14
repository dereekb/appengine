package com.dereekb.gae.client.api.auth.token.exception;

import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;

/**
 * Thrown if the token has expired.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenExpiredException extends ClientLoginTokenValidationException {

	private static final long serialVersionUID = 1L;

	public ClientLoginTokenExpiredException(ClientResponseErrorInfo error) {
		super(error);
	}

}
