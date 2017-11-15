package com.dereekb.gae.server.auth.security.roles.authority;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.utilities.collections.set.dencoder.EncodedLongDecoder;

/**
 * {@link EncodedLongDecoder} for {@link GrantedAuthority}.
 *
 * @author dereekb
 *
 */
public interface GrantedAuthorityDecoder
        extends EncodedLongDecoder<GrantedAuthority> {}
