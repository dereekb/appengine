package com.dereekb.gae.server.auth.security.model.context.encoded;

import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.exception.UnavailableEncodedModelContextException;

/**
 * Encoded {@link LoginTokenModelContextSet}.
 * 
 * @author dereekb
 *
 */
public interface EncodedLoginTokenModelContextSet {

	/**
	 * Returns the set of encoded context types.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<Integer> getEncodedModelContextTypes();

	/**
	 * Returns an encoded model context for the input.
	 * 
	 * @return {@link String}. Never {@code null}.
	 * @throws UnavailableEncodedModelContextException
	 *             thrown if the encoded value does not exist.
	 */
	public String getEncodedModelTypeContext(Integer encodedType) throws UnavailableEncodedModelContextException;

}
