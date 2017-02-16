package com.dereekb.gae.server.auth.security.token.parameter.impl;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.token.exception.TokenMissingException;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterService;
import com.dereekb.gae.server.auth.security.token.parameter.exception.InvalidAuthStringException;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;

/**
 * {@link AuthenticationParameterService} implementation.
 * 
 * @author dereekb
 *
 */
public class AuthenticationParameterServiceImpl
        implements AuthenticationParameterService {

	public static final String DEFAULT_BEARER_PREFIX = "Bearer ";
	public static final String DEFAULT_HEADER_KEY = "Authorization";

	public static final AuthenticationParameterService SINGLETON = new AuthenticationParameterServiceImpl();

	private String headerKey = DEFAULT_HEADER_KEY;
	private String bearerPrefix = DEFAULT_BEARER_PREFIX;

	private int bearerPrefixLength = DEFAULT_BEARER_PREFIX.length();

	public AuthenticationParameterServiceImpl() {}

	public AuthenticationParameterServiceImpl(String headerKey, String bearerPrefix) throws IllegalArgumentException {
		this.setHeaderKey(headerKey);
		this.setBearerPrefix(bearerPrefix);
	}

	public String getHeaderKey() {
		return this.headerKey;
	}

	public void setHeaderKey(String headerKey) throws IllegalArgumentException {
		if (headerKey == null || headerKey.isEmpty()) {
			throw new IllegalArgumentException("HeaderKey cannot be null or empty.");
		}

		this.headerKey = headerKey;
	}

	public String getBearerPrefix() {
		return this.bearerPrefix;
	}

	public void setBearerPrefix(String bearerPrefix) throws IllegalArgumentException {
		if (bearerPrefix == null) {
			throw new IllegalArgumentException("Bearer prefix cannot be null.");
		}

		this.bearerPrefix = bearerPrefix;
		this.bearerPrefixLength = this.bearerPrefix.length();
	}

	// MARK: Reader
	@Override
	public String getAuthenticationHeaderKey() {
		return this.headerKey;
	}

	@Override
	public String readToken(HttpServletRequest request) throws TokenMissingException, InvalidAuthStringException {
		String header = request.getHeader(this.headerKey);

		if (header == null) {
			throw new TokenMissingException();
		}

		return this.readToken(header);
	}

	@Override
	public String readToken(String parameter) throws InvalidAuthStringException {

		if (parameter == null || !parameter.startsWith(this.bearerPrefix)) {
			throw new InvalidAuthStringException();
		}

		return parameter.substring(this.bearerPrefixLength);
	}

	// MARK: Builder

	@Override
	public KeyedEncodedParameter buildAuthenticationParameter(String token) {
		if (token == null) {
			throw new IllegalArgumentException("Token cannot be null.");
		}

		String value = this.bearerPrefix + token;
		return new KeyedEncodedParameterImpl(this.headerKey, value);
	}

	@Override
	public KeyedEncodedParameter buildAuthenticationParameter(EncodedLoginToken token) {
		return this.buildAuthenticationParameter(token.getEncodedLoginToken());
	}

	@Override
	public String toString() {
		return "AuthenticationParameterServiceImpl [headerKey=" + this.headerKey + ", bearerPrefix=" + this.bearerPrefix
		        + "]";
	}

}
