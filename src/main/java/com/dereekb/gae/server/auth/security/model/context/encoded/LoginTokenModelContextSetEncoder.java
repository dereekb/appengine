package com.dereekb.gae.server.auth.security.model.context.encoded;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;

/**
 * Used to encode an {@link EncodedLoginTokenModelContextSet} from a
 * {@link LoginTokenModelContextSet}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextSetEncoder {

	/**
	 * Encodes the input set.
	 * 
	 * @param set
	 *            {@link LoginTokenModelContextSet}. Never {@code null}.
	 * @return {@link EncodedLoginTokenModelContextSet}. Never {@code null}.
	 */
	public EncodedLoginTokenModelContextSet encodeSet(LoginTokenModelContextSet set);

}
