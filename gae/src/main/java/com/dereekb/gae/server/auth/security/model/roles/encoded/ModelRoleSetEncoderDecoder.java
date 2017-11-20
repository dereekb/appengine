package com.dereekb.gae.server.auth.security.model.roles.encoded;

import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;

/**
 * Used for encoding and decoding a {@link ModelRoleSet}.
 * 
 * @author dereekb
 *
 */
public interface ModelRoleSetEncoderDecoder {

	/**
	 * Encodes the input {@link ModelRoleSet}.
	 * 
	 * @param roleSet
	 *            {@link ModelRoleSet}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String encodeRoleSet(ModelRoleSet roleSet);

	/**
	 * Decodes the input as a set of {@link ModelRole} values.
	 * 
	 * @param encodedRoleSet
	 *            {@link String}. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelRole> decodeRoleSet(String encodedRoleSet);

}
