package com.dereekb.gae.server.auth.security.token.model;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Service used for encoding, decode, and building {@link LoginToken} values.
 *
 * @author dereekb
 *
 */
public interface LoginTokenService<T extends LoginToken>
        extends LoginTokenBuilder<T>, LoginTokenEncoderDecoder<T> {

	/**
	 * Builds and encodes a token.
	 *
	 * @param anonymousId
	 *            Optional anonymous identifier. May be {@code null}.
	 * @return Encoded string token. Never {@code null}.
	 */
	public String encodeAnonymousLoginToken(String anonymousId);

	/**
	 * Builds and encodes a {@link LoginToken} from a {@link LoginPointer}.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @return Encoded string token. Never {@code null}.
	 */
	public String encodeLoginToken(LoginPointer pointer,
	                               boolean refreshAllowed);

}
