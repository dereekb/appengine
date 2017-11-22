package com.dereekb.gae.server.auth.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * Used for retrieving the {@link LoginTokenAuthentication} instance.
 *
 * @author dereekb
 *
 */
public class LoginSecurityContext {

	public static boolean safeIsAdministrator() {
		try {
			return isAdministrator();
		} catch (NoSecurityContextException e) {
			return false;
		}
	}

	public static boolean isAdministrator() throws NoSecurityContextException {
		LoginTokenAuthentication<LoginToken> authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails<LoginToken> principal = authentication.getPrincipal();
		return principal.isAdministrator();
	}

	public static LoginTokenUserDetails<LoginToken> getPrincipal() throws NoSecurityContextException {
		return LoginSecurityContext.getAuthentication().getPrincipal();
	}

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
