package com.dereekb.gae.server.auth.old.gae.security.authentication;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.appengine.api.users.User;

/**
 * Extension of {@link PreAuthenticatedAuthenticationToken} used for
 * {@link User}.
 *
 * @author dereekb
 */
public final class GoogleAccountAuthentication extends PreAuthenticatedAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private final User user;

	public GoogleAccountAuthentication(User user) {
		super(user, null);
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

}
