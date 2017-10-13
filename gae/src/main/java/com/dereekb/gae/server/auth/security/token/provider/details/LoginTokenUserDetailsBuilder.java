package com.dereekb.gae.server.auth.security.token.provider.details;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Used for building user details.
 * 
 * @author dereekb
 *
 * @param <T>
 *            token type
 */
public interface LoginTokenUserDetailsBuilder<T extends LoginToken>
        extends AbstractLoginTokenUserDetailsBuilder<LoginTokenUserDetails<T>, T> {}
