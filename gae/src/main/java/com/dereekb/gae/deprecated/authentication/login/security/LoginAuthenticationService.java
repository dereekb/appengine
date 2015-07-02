package com.thevisitcompany.gae.deprecated.authentication.login.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSource;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSourceService;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;

/**
 * Helper class for retrieving the current authentication.
 * 
 * @author dereekb
 * 
 */
public class LoginAuthenticationService
        implements LoginSource, LoginSourceService {

	public static boolean isAuthenticated() {
		boolean isAuthenticated = false;
		Authentication authentication = getAuthentication();

		if (authentication != null) {
			isAuthenticated = authentication.isAuthenticated();
		}

		return isAuthenticated;
	}

	public static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return authentication;
	}

	public static LoginAuthentication getLoginAuthentication() {
		LoginAuthentication loginAuthentication = null;

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();

		if (authentication != null && (authentication instanceof LoginAuthentication)) {
			loginAuthentication = (LoginAuthentication) authentication;
		}

		return loginAuthentication;
	}

	public static boolean isLoggedIn() {
		return (getCurrentLogin() != null);
	}

	public static Login getCurrentLogin() {
		Login login = null;

		LoginAuthentication loginAuthentication = getLoginAuthentication();

		if (loginAuthentication != null) {
			login = (Login) loginAuthentication.getPrincipal();
		}

		return login;
	}

	public LoginAuthenticationService() {}

	@Override
	public LoginSource getLoginDelegate() {
		return this;
	}

	@Override
	public Login getLogin() {
		return getCurrentLogin();
	}

}
