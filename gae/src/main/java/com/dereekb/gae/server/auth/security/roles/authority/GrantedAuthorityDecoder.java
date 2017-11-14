package com.dereekb.gae.server.auth.security.roles.authority;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

/**
 * Used for decoding encoded granted authorities.
 *
 * @author dereekb
 *
 */
public interface GrantedAuthorityDecoder {

	/**
	 * Decodes the input encoded roles into a {@link GrantedAuthority}
	 * {@link Set}.
	 * 
	 * @param encodedRoles
	 *            Roles encoded within a {@link Long}. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<GrantedAuthority> decodeRoles(Long encodedRoles);

}
