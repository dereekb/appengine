package com.dereekb.gae.web.api.auth.controller.token;

/**
 * {@link LoginTokenController} validation request.s
 *
 * @author dereekb
 *
 */
public interface TokenValidationRequest {

	/**
	 * Returns the token.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getToken();

	/**
	 * Returns the content, if applicable.
	 *
	 * @return {@link String}, or {@code null}.
	 */
	public String getContent();

	/**
	 * Returns the signature, if applicable.
	 *
	 * @return {@link String}, or {@code null}.
	 */
	public String getSignature();

	/**
	 * Whether or not the response should only validate, or should also return
	 * JSON info about login token.
	 *
	 * @return {@link Boolean}.
	 */
	public Boolean getQuick();

}
