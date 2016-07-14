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

	public Set<GrantedAuthority> decodeRoles(Long encodedRoles);

}
