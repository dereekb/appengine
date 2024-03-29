package com.dereekb.gae.server.auth.security.token.parameter;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.token.exception.TokenMissingException;
import com.dereekb.gae.server.auth.security.token.exception.TokenSignatureInvalidException;
import com.dereekb.gae.server.auth.security.token.parameter.exception.InvalidAuthStringException;

/**
 * Used for reading a header parameter for a token.
 *
 * @author dereekb
 *
 */
public interface AuthenticationParameterReader {

	/**
	 * Returns the key for the header that will contain the authentication.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAuthenticationHeaderKey();

	/**
	 * Returns the key for the header that will contain the signature.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getSignatureHeaderKey();

	/**
	 * Attempts to read the token from the input servlet request.
	 *
	 * @param request
	 *            {@link HttpServletRequest}. Never {@code null}.
	 * @return {@link String} token. Never {@code null}.
	 * @throws TokenMissingException
	 *             thrown if the token is missing from the request.
	 * @throws InvalidAuthStringException
	 *             thrown if the input was invalid and/or did not contain a
	 *             token.
	 */
	public String readToken(HttpServletRequest request) throws TokenMissingException, InvalidAuthStringException;

	/**
	 * Attempts to read the signature from the input servlet request.
	 *
	 * @param request
	 *            {@link HttpServletRequest}. Never {@code null}.
	 * @throws TokenSignatureInvalidException
	 *             thrown if the input is invalid or not contained in the
	 *             request.
	 */
	public String readSignature(HttpServletRequest request) throws TokenSignatureInvalidException;

	/**
	 * Attempts to read the token from the input string.
	 *
	 * @param parameter
	 *            {@link String}. Never {@code null}.
	 * @return {@link String} token. Never {@code null}.
	 * @throws InvalidAuthStringException
	 *             thrown if the input was invalid and/or did not contain a
	 *             token.
	 */
	public String readToken(String parameter) throws InvalidAuthStringException;

	// TODO: Read content?

}
