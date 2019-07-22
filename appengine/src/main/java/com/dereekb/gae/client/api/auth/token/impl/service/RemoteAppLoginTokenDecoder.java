package com.dereekb.gae.client.api.auth.token.impl.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationResponse;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationService;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenExpiredException;
import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationRequestImpl;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.filter.AuthenticationLoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterReader;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * {@link LoginTokenDecoder} implementation that uses a
 * {@link ClientLoginTokenValidationService} to decode using a remote server.
 *
 * @author dereekb
 *
 * @deprecated Does not fit into the system properly as a
 *             {@link LoginTokenDecoder} does not have access to the signature
 *             to properly validate remotely.
 * @param <T>
 *            model type
 */
@Deprecated
public class RemoteAppLoginTokenDecoder<T extends LoginToken>
        implements LoginTokenDecoder<T>, AuthenticationLoginTokenDecoder<T> {

	private LoginTokenDecoder<T> decoder;
	private ClientLoginTokenValidationService clientValidationService;
	private AuthenticationParameterReader authenticationParameterReader = AuthenticationParameterServiceImpl.SINGLETON;

	public RemoteAppLoginTokenDecoder(LoginTokenDecoder<T> decoder,
	        ClientLoginTokenValidationService clientValidationService) {
		super();
		this.setDecoder(decoder);
		this.setClientValidationService(clientValidationService);
	}

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

	public AuthenticationParameterReader getAuthenticationParameterReader() {
		return this.authenticationParameterReader;
	}

	public void setAuthenticationParameterReader(AuthenticationParameterReader authenticationParameterReader) {
		if (authenticationParameterReader == null) {
			throw new IllegalArgumentException("authenticationParameterReader cannot be null.");
		}

		this.authenticationParameterReader = authenticationParameterReader;
	}

	// MARK: ClientLoginTokenValidationService
	@Override
	public DecodedLoginToken<T> decodeLoginToken(String token)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {

		ClientLoginTokenValidationRequestImpl validationRequest = new ClientLoginTokenValidationRequestImpl(token,
		        true);
		return this.validateToken(validationRequest);
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

	// MARK: AuthenticationLoginTokenDecoder
	@Override
	public DecodedLoginToken<T> authenticateLoginToken(String token,
	                                                   HttpServletRequest request)
	        throws TokenExpiredException,
	            TokenUnauthorizedException,
	            TokenException {
		String signature = this.authenticationParameterReader.readSignature(request);
		ClientLoginTokenValidationRequest validationRequest = new ClientLoginTokenValidationRequestImpl(token,
		        signature, true);
		return this.validateToken(validationRequest);
	}

	// MARK: Internal
	protected DecodedLoginToken<T> validateToken(ClientLoginTokenValidationRequest request) throws TokenException {
		try {
			ClientLoginTokenValidationResponse response = this.clientValidationService.validateToken(request);
			Map<String, String> claimsMap = response.getClaimsMap();
			Claims claims = Jwts.claims(new HashMap<String, Object>(claimsMap));
			return this.decoder.decodeLoginTokenFromClaims(request.getToken(), claims);
		} catch (ClientLoginTokenExpiredException e) {
			throw new TokenExpiredException();
		} catch (ClientRequestFailureException e) {
			throw new TokenUnauthorizedException();
		} catch (UnsupportedOperationException e) {
			throw new IllegalArgumentException("Did you forgot to ask for claims?", e);
		}
	}

	@Override
	public String toString() {
		return "RemoteAppLoginTokenDecoder [decoder=" + this.decoder + ", clientValidationService="
		        + this.clientValidationService + ", authenticationParameterReader=" + this.authenticationParameterReader
		        + "]";
	}

}
