package com.dereekb.gae.server.auth.old.gae.security.authentication;

import com.dereekb.gae.server.auth.deprecated.old.security.authentication.LoginAuthentication;
import com.dereekb.gae.server.auth.deprecated.old.security.authentication.LoginAuthenticationProviderDelegate;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Implementation of {@link LoginAuthenticationProviderDelegate} for used with
 * Google Accounts logins.
 *
 * Compares the {@link LoginPointer} on the {@link LoginAuthentication} to the
 * {@link User} email.
 *
 * @author dereekb
 */
@Deprecated
public class GoogleAccountLoginAuthenticationProviderDelegate
        implements LoginAuthenticationProviderDelegate {

	@Override
	public boolean isStillAuthorized(LoginAuthentication loginAuth) {
		boolean authorized = false;

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		LoginPointer loginPointer = loginAuth.getLoginPointer();

		if (user != null && loginPointer != null) {
			ModelKey key = loginPointer.getModelKey();
			String loginEmail = key.getName();
			String email = user.getEmail();
			authorized = (email.equals(loginEmail));
		}

		return authorized;
	}

}
