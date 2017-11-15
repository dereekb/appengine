package com.dereekb.gae.server.auth.security.model.context.encoded;

import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;

/**
 * Used for encoding and decoding a {@link LoginTokenModelContextRoleSet}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextRoleSetEncoderDecoder {

	/**
	 * Encodes the input {@link LoginTokenModelContextRoleSet}.
	 * 
	 * @param roleSet
	 *            {@link LoginTokenModelContextRoleSet}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String encodeRoleSet(LoginTokenModelContextRoleSet roleSet);

	/**
	 * Decodes the input as a set of {@link LoginTokenModelContextRole} values.
	 * 
	 * @param encodedRoleSet
	 *            {@link String}. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<LoginTokenModelContextRole> decodeRoleSet(String encodedRoleSet);

}
