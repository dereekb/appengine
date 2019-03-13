package com.dereekb.gae.client.api.auth.token;

/**
 * {@link ClientLoginTokenValidationService} request.
 *
 * @author dereekb
 *
 */
public interface ClientLoginTokenValidationRequest {

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
	 * the deserialized claims.
	 *
	 * @return {@code true} if claims should be returned.
	 */
	public Boolean getIncludeClaims();

}
