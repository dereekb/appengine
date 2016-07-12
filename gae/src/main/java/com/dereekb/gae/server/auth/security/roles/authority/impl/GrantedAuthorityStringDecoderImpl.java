package com.dereekb.gae.server.auth.security.roles.authority.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.set.SetDecoder;

/**
 * {@link GrantedAuthorityDecoder} implementation.
 *
 * @author dereekb
 */
public class GrantedAuthorityStringDecoderImpl  implements GrantedAuthorityDecoder {

	private SetDecoder<String> rolesDecoder;

	public SetDecoder<String> getRolesDecoder() {
		return this.rolesDecoder;
	}

	public void setRolesDecoder(SetDecoder<String> rolesDecoder) {
		this.rolesDecoder = rolesDecoder;
	}

	// MARK: GrantedAuthorityDecoder
	@Override
	public Set<GrantedAuthority> decodeRoles(Iterable<Integer> encodedRoles) {
		List<String> encodedStringRoles = IteratorUtility.iterableToStrings(encodedRoles);
		return this.decode(encodedStringRoles);
	}

	// MARK: SetDecoder
	@Override
	public Set<GrantedAuthority> decode(String encodedRoles) {
		Set<String> decoded = this.rolesDecoder.decode(encodedRoles);
		return this.convertToAuthority(decoded);
	}

	@Override
	public Set<GrantedAuthority> decode(Iterable<String> encodedRoles) {
		Set<String> decoded = this.rolesDecoder.decode(encodedRoles);
		return this.convertToAuthority(decoded);
	}

	public Set<GrantedAuthority> convertToAuthority(Set<String> roles) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}

	@Override
	public String toString() {
		return "GrantedAuthorityDecoderImpl [rolesDecoder=" + this.rolesDecoder + "]";
	}

}
