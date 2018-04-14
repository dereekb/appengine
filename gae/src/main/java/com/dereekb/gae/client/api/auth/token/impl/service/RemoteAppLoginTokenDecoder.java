package com.dereekb.gae.client.api.auth.token.impl.service;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationResponse;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationService;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenExpiredException;
import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationRequestImpl;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * {@link LoginTokenDecoder} implementation that uses a
 * {@link ClientLoginTokenValidationService} to decode using a remote server.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class RemoteAppLoginTokenDecoder<T extends LoginToken>
        implements LoginTokenDecoder<T> {

	private LoginTokenDecoder<T> decoder;
	private ClientLoginTokenValidationService clientValidationService;

	public LoginTokenDecoder<T> getDecoder() {
		return this.decoder;
	}

	public void setDecoder(LoginTokenDecoder<T> decoder) {
		if (decoder == null) {
			throw new IllegalArgumentException("decoder cannot be null.");
		}

		this.decoder = decoder;
	}

	public ClientLoginTokenValidationService getClientValidationService() {
		return this.clientValidationService;
	}

	public void setClientValidationService(ClientLoginTokenValidationService clientValidationService) {
		if (clientValidationService == null) {
			throw new IllegalArgumentException("clientValidationService cannot be null.");
		}

		this.clientValidationService = clientValidationService;
	}

	// MARK: ClientLoginTokenValidationService
	@Override
	public DecodedLoginToken<T> decodeLoginToken(String token)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {

		ClientLoginTokenValidationRequest validationRequest = new ClientLoginTokenValidationRequestImpl(token, true);

		try {
			ClientLoginTokenValidationResponse response = this.clientValidationService.validateToken(validationRequest);
			Map<String, String> claimsMap = response.getClaimsMap();
			Claims claims = Jwts.claims(new HashMap<String, Object>(claimsMap));
			return this.decoder.decodeLoginTokenFromClaims(token, claims);
		} catch (ClientLoginTokenExpiredException e) {
			throw new TokenExpiredException();
		} catch (ClientRequestFailureException e) {
			throw new TokenUnauthorizedException();
		}
	}

	@Override
	public DecodedLoginToken<T> decodeLoginTokenFromClaims(String token,
	                                                       Claims claims)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {
		return this.decoder.decodeLoginTokenFromClaims(token, claims);
	}

	@Override
	public DecodedLoginToken<T> decodeLoginTokenFromClaims(Claims claims)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {
		throw new UnsupportedOperationException();
	}

}
