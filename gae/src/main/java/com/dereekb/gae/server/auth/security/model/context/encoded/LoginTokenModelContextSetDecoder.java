package com.dereekb.gae.server.auth.security.model.context.encoded;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;

/**
 * Used to decode a {@link LoginTokenModelContextSet} from an
 * {@link EncodedLoginTokenModelContextSet}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextSetDecoder {

	/**
	 * Decodes the input set.
	 * 
	 * @param set
	 *            {@link EncodedLoginTokenModelContextSet}. Never {@code null}.
	 * @return {@link LoginTokenModelContextSet}. Never {@code null}.
	 */
	public LoginTokenModelContextSet decodeSet(EncodedLoginTokenModelContextSet set);

}
