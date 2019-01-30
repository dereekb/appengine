package com.dereekb.gae.client.api.auth.token;

import java.util.Map;

/**
 * {@link ClientLoginTokenValidationService} response.
 *
 * @author dereekb
 *
 */
public interface ClientLoginTokenValidationResponse {

	/**
	 * @return {@link Map} of claims. Never {@code null}.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown if claims were not requested.
	 */
	public Map<String, String> getClaimsMap() throws UnsupportedOperationException;

}
