package com.dereekb.gae.server.auth.security.roles.authority.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.utilities.collections.set.dencoder.impl.EncodedLongDecoderImpl;
import com.dereekb.gae.utilities.collections.set.dencoder.impl.StringSetDecoderImpl;

/**
 * {@link GrantedAuthorityDecoder} implementation that directly extends
 * {@link StringSetDecoderImpl} and contains a map of {@link GrantedAuthority} values.
 *
 * @author dereekb
 *
 */
public class GrantedAuthorityDecoderImpl extends EncodedLongDecoderImpl<GrantedAuthority>
        implements GrantedAuthorityDecoder {

	public GrantedAuthorityDecoderImpl(Map<Integer, GrantedAuthority> map) throws IllegalArgumentException {
		super(map);
	}

	public static GrantedAuthorityDecoderImpl withStringMap(Map<Integer, String> map) throws IllegalArgumentException {
		if (map == null) {
			throw new IllegalArgumentException("Map cannot be null.");
		}

		Map<Integer, GrantedAuthority> authorityMap = new HashMap<Integer, GrantedAuthority>();

		for (Entry<Integer, String> entry : map.entrySet()) {
			authorityMap.put(entry.getKey(), new SimpleGrantedAuthority(entry.getValue()));;
		}

		return new GrantedAuthorityDecoderImpl(authorityMap);
	}

	// MARK: GrantedAuthorityDecoder
	@Override
	public Set<GrantedAuthority> decodeRoles(Long encoded) {
		return super.decode(encoded);
	}

	@Override
	public String toString() {
		return "GrantedAuthorityDecoderImpl [map=" + this.getMap() + "]";
	}

}
