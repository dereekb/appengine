package com.dereekb.gae.server.auth.security.roles.authority.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.set.impl.SetDecoderImpl;

/**
 * {@link GrantedAuthorityDecoder} implementation that directly extends
 * {@link SetDecoderImpl} and contains a map of {@link GrantedAuthority} values.
 *
 * @author dereekb
 *
 */
public class GrantedAuthorityDecoderImpl extends SetDecoderImpl<GrantedAuthority>
        implements GrantedAuthorityDecoder {

	public GrantedAuthorityDecoderImpl(Map<String, GrantedAuthority> map) throws IllegalArgumentException {
		super(map);
	}

	public GrantedAuthorityDecoderImpl withStringMap(Map<String, String> map) throws IllegalArgumentException {
		if (map == null) {
			throw new IllegalArgumentException("Map cannot be null.");
		}

		Map<String, GrantedAuthority> authorityMap = new HashMap<String, GrantedAuthority>();

		for (Entry<String, String> entry : map.entrySet()) {
			authorityMap.put(entry.getKey(), new SimpleGrantedAuthority(entry.getValue()));;
		}

		return new GrantedAuthorityDecoderImpl(authorityMap);
	}

	// MARK: GrantedAuthorityDecoder
	@Override
	public Set<GrantedAuthority> decodeRoles(Iterable<Integer> encodedRoles) {
		List<String> encodedStringRoles = IteratorUtility.iterableToStrings(encodedRoles);
		return this.decode(encodedStringRoles);
	}

	@Override
	public String toString() {
		return "GrantedAuthorityDecoderImpl []";
	}

}
