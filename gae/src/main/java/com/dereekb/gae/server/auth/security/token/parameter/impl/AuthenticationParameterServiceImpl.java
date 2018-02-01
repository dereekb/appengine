package com.dereekb.gae.server.auth.security.token.parameter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.token.exception.TokenMissingException;
import com.dereekb.gae.server.auth.security.token.exception.TokenSignatureInvalidException;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterService;
import com.dereekb.gae.server.auth.security.token.parameter.exception.InvalidAuthStringException;
import com.dereekb.gae.utilities.data.StringUtility;
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

	public static final String DEFAULT_SIGNATURE_HEADER = "app_proof";

	public static final AuthenticationParameterService SINGLETON = new AuthenticationParameterServiceImpl();

	private String headerKey = DEFAULT_HEADER_KEY;
	private String bearerPrefix = DEFAULT_BEARER_PREFIX;

	private String signatureKey = DEFAULT_SIGNATURE_HEADER;

	private transient int bearerPrefixLength = DEFAULT_BEARER_PREFIX.length();

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

	public String getSignatureKey() {
		return this.signatureKey;
	}

	public void setSignatureKey(String signatureKey) {
		if (signatureKey == null) {
			throw new IllegalArgumentException("signatureKey cannot be null.");
		}

		this.signatureKey = signatureKey;
	}

	// MARK: Reader
	@Override
	public String getAuthenticationHeaderKey() {
		return this.headerKey;
	}

	@Override
	public String getSignatureHeaderKey() {
		return this.signatureKey;
	}

	@Override
	public String readToken(HttpServletRequest request) throws TokenMissingException, InvalidAuthStringException {
		String header = request.getHeader(this.headerKey);

		if (StringUtility.isEmptyString(header)) {
			throw new TokenMissingException();
		}

		return this.readToken(header);
	}

	@Override
	public String readSignature(HttpServletRequest request) throws TokenSignatureInvalidException {
		String header = request.getHeader(this.signatureKey);

		if (StringUtility.isEmptyString(header)) {
			throw new TokenSignatureInvalidException("App signature header is missing.");
		}

		return header;
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
	public KeyedEncodedParameter buildTokenAuthenticationParameter(String token) throws IllegalArgumentException {
		if (token == null) {
			throw new IllegalArgumentException("Token cannot be null.");
		}

		String value = this.bearerPrefix + token;
		return new KeyedEncodedParameterImpl(this.headerKey, value);
	}

	@Override
	public KeyedEncodedParameter buildSignatureAuthenticationParameter(String signature) {
		if (signature == null) {
			throw new IllegalArgumentException("Signature cannot be null.");
		}

		return new KeyedEncodedParameterImpl(this.signatureKey, signature);
	}

	@Override
	public List<KeyedEncodedParameter> buildAuthenticationParameters(EncodedLoginToken token)
	        throws IllegalArgumentException {
		List<KeyedEncodedParameter> parameters = new ArrayList<KeyedEncodedParameter>();

		String encodedToken = token.getEncodedLoginToken();
		parameters.add(this.buildTokenAuthenticationParameter(encodedToken));

		String signature = token.getTokenSignature();

		if (signature != null) {
			parameters.add(this.buildSignatureAuthenticationParameter(signature));
		}

		return parameters;
	}

	@Override
	public String toString() {
		return "AuthenticationParameterServiceImpl [headerKey=" + this.headerKey + ", bearerPrefix=" + this.bearerPrefix
		        + ", signatureKey=" + this.signatureKey + "]";
	}

}
