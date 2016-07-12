package com.dereekb.gae.server.auth.security.roles.authority;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.utilities.collections.set.SetDecoder;

/**
 * Used for decoding encoded granted authorities.
 *
 * @author dereekb
 *
 */
public interface GrantedAuthorityDecoder
        extends SetDecoder<GrantedAuthority> {

	public Set<GrantedAuthority> decodeRoles(Iterable<Integer> encodedRoles);

}
