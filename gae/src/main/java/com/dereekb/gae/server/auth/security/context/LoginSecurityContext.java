package com.dereekb.gae.server.auth.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;

/**
 * Used for retrieving the {@link LoginTokenAuthentication} instance.
 *
 * @author dereekb
 *
 */
public class LoginSecurityContext {

	public static LoginTokenAuthentication<LoginToken> getAuthentication() throws NoSecurityContextException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new NoSecurityContextException();
		}

		@SuppressWarnings("unchecked")
		LoginTokenAuthentication<LoginToken> cast = (LoginTokenAuthentication<LoginToken>) authentication;
		return cast;
	}

}
