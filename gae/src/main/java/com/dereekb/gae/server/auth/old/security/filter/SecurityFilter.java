package com.dereekb.gae.server.auth.old.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.dereekb.gae.server.auth.old.security.filter.exception.NewLoginAuthenticationException;
import com.dereekb.gae.server.auth.old.security.registration.NewLoginHandler;

/**
 * Default security filter.
 *
 * @author dereekb
 */
public final class SecurityFilter extends GenericFilterBean {

	private NewLoginHandler newLoginHandler;
	private AuthenticationManager authenticationManager;
	private AuthenticationFailureHandler failureHandler;

	public SecurityFilter() {}

	public AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	public NewLoginHandler getNewLoginHandler() {
		return this.newLoginHandler;
	}

	public void setNewLoginHandler(NewLoginHandler newLoginHandler) {
		this.newLoginHandler = newLoginHandler;
	}

	public AuthenticationFailureHandler getFailureHandler() {
		return this.failureHandler;
	}

	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}

	@Override
	public void doFilter(ServletRequest request,
	                     ServletResponse response,
	                     FilterChain chain) throws IOException, ServletException {

		SecurityContext context = SecurityContextHolder.getContext();

		try {

			Authentication authentication = this.handleAuthentication(request, response, context);
			context.setAuthentication(authentication);

		} catch (AuthenticationException e) {

			HttpServletRequest httpReq = (HttpServletRequest) request;
			HttpServletResponse httpResp = (HttpServletResponse) response;
			this.failureHandler.onAuthenticationFailure(httpReq, httpResp, e);
			return;
		}

		chain.doFilter(request, response);
	}

	private Authentication handleAuthentication(ServletRequest request,
	                                            ServletResponse response,
	                                            SecurityContext context) {
		Authentication authentication = context.getAuthentication();

		try {
			if (authentication == null) {
				// Create a new authentication token to begin authentication.
				PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(null, null);
				authentication = this.authenticationManager.authenticate(token);

			} else {

				// Verify the current authentication.
				authentication = this.authenticationManager.authenticate(authentication);
			}
		} catch (NewLoginAuthenticationException e) {

			HttpServletRequest httpReq = (HttpServletRequest) request;
			HttpServletResponse httpResp = (HttpServletResponse) response;
			authentication = this.newLoginHandler.handleNewLogin(e, httpReq, httpResp);
		}

		return authentication;
	}

	@Override
	public void afterPropertiesSet() throws ServletException {
		Assert.notNull(this.failureHandler, "AuthenticationFailureHandler must be set");
		Assert.notNull(this.authenticationManager, "AuthenticationManager must be set");
		Assert.notNull(this.newLoginHandler, "NewLoginHandler must be set");
	}

}
