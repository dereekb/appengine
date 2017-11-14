package com.dereekb.gae.server.auth.security.model.context.encoded;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;

/**
 * Encoded set of {@link LoginTokenModelContext} of a specific type.
 * 
 * @author dereekb
 *
 */
public interface EncodedLoginTokenTypedModelContext {

	/**
	 * Returns the encoded model type.
	 * 
	 * @return {@link Integer}. Never {@code null}.
	 */
	public Integer getEncodedModelType();

	/**
	 * Returns the encoded context set string.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEncodedModelContext();

}
