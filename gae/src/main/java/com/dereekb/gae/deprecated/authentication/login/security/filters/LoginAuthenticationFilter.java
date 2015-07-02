package com.thevisitcompany.gae.deprecated.authentication.login.security.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.thevisitcompany.gae.deprecated.authentication.login.AuthenticationReader;
import com.thevisitcompany.gae.deprecated.authentication.login.security.LoginAuthentication;
import com.thevisitcompany.gae.deprecated.authentication.registration.RegistrationHandler;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.NewUserRole;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class LoginAuthenticationFilter extends GenericFilterBean {

	private static final String HOME_URL = "/";
	private static final String REGISTRATION_URL = "/register";
	private static final String NEW_USER_ROLE = NewUserRole.ROLE_NAME;

	private final Logger logger = Logger.getLogger(LoginAuthenticationFilter.class.getName());

	private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();
	private AuthenticationManager authenticationManager;
	private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	@Override
	public void doFilter(ServletRequest request,
	                     ServletResponse response,
	                     FilterChain chain) throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		UserService userService = UserServiceFactory.getUserService();
		User googleUser = userService.getCurrentUser();

		if (authentication == null) {
			if (googleUser != null) {
				logger.info("Currently logged on to GAE as user " + googleUser);

				// User has returned after authenticating via GAE. Need to authenticate through Spring Security.
				PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
				token.setDetails(ads.buildDetails((HttpServletRequest) request));

				try {
					authentication = authenticationManager.authenticate(token);
					SecurityContextHolder.getContext().setAuthentication(authentication);

					this.checkForNewUser(authentication, (HttpServletRequest) request, (HttpServletResponse) response);
				} catch (AuthenticationException e) {
					failureHandler.onAuthenticationFailure((HttpServletRequest) request,
					        (HttpServletResponse) response, e);
					return;
				}

			} else {
				// Redirect to alternate login.
			}

		} else {
			if (!loggedInUserMatchesUser(authentication, googleUser)) {
				SecurityContextHolder.clearContext();
				authentication = null;
				((HttpServletRequest) request).getSession().invalidate();
			}
		}

		chain.doFilter(request, response);
	}

	private void checkForNewUser(Authentication authentication,
	                             HttpServletRequest request,
	                             HttpServletResponse response) throws IOException {

		LoginAuthentication loginAuthentication = (LoginAuthentication) authentication;
		AuthenticationReader authenticationReader = new AuthenticationReader(loginAuthentication);

		boolean isNewUser = authenticationReader.hasAuthority(NEW_USER_ROLE);

		if (isNewUser) {
			boolean isAdmin = UserServiceFactory.getUserService().isUserAdmin();

			if (isAdmin) {
				this.autoRegisterAdministrator(loginAuthentication, request, response);
			} else {
				response.sendRedirect(REGISTRATION_URL);
				return;
			}
		}
	}

	private void autoRegisterAdministrator(LoginAuthentication loginAuthentication,
	                                       HttpServletRequest request,
	                                       HttpServletResponse response) throws IOException {

		logger.info("New admin authenticated. Automatically authenticating and continuing to service.");
		Login newUser = (Login) loginAuthentication.getPrincipal();

		RegistrationHandler registrationHandler = new RegistrationHandler();
		Login administrator = registrationHandler.registerAdministrator(newUser);

		Object details = loginAuthentication.getDetails();
		LoginAuthentication newAuthentication = new LoginAuthentication(administrator, details);

		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		response.sendRedirect(HOME_URL);
	}

	private boolean loggedInUserMatchesUser(Authentication authentication,
	                                        User googleUser) {
		assert authentication != null;

		if (googleUser == null) {
			// User has logged out of GAE but is still logged into application
			return false;
		}

		Login login = (Login) authentication.getPrincipal();

		if (!login.getEmail().equals(googleUser.getEmail())) {
			return false;
		}

		return true;
	}

	@Override
	public void afterPropertiesSet() throws ServletException {
		Assert.notNull(authenticationManager, "AuthenticationManager must be set");
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}
}
