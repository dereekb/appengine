package com.dereekb.gae.server.auth.security.token.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenMissingException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterService;
import com.dereekb.gae.server.auth.security.token.parameter.exception.InvalidAuthStringException;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;

/**
 * Authentication filter for filtering security by using a {@link LoginToken}.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFilter extends GenericFilterBean {

	private final WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private AuthenticationSuccessHandler successHandler;
	private AuthenticationFailureHandler failureHandler;

	private AuthenticationParameterService authenticationParameterService = AuthenticationParameterServiceImpl.SINGLETON;
	private LoginTokenAuthenticationFilterDelegate delegate;

	public LoginTokenAuthenticationFilter() {}

	public LoginTokenAuthenticationFilter(LoginTokenAuthenticationFilterDelegate delegate,
	        AuthenticationSuccessHandler successHandler,
	        AuthenticationFailureHandler failureHandler) throws IllegalArgumentException {
		this.setDelegate(delegate);
		this.setSuccessHandler(successHandler);
		this.setFailureHandler(failureHandler);
	}

	public AuthenticationSuccessHandler getSuccessHandler() {
		return this.successHandler;
	}

	public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
		this.successHandler = successHandler;
	}

	public AuthenticationFailureHandler getFailureHandler() {
		return this.failureHandler;
	}

	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}

	public LoginTokenAuthenticationFilterDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LoginTokenAuthenticationFilterDelegate delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public AuthenticationParameterService getAuthenticationParameterService() {
		return this.authenticationParameterService;
	}

	public void setAuthenticationParameterService(AuthenticationParameterService authenticationParameterService) {
		if (authenticationParameterService == null) {
			throw new IllegalArgumentException("authenticationParameterService cannot be null.");
		}

		this.authenticationParameterService = authenticationParameterService;
	}

	// MARK: GenericFilterBean
	@Override
	public void doFilter(ServletRequest req,
	                     ServletResponse res,
	                     FilterChain chain)
	        throws IOException,
	            ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		Authentication authentication;

		try {
			authentication = this.attemptAuthentication(request, response);
			this.successfulAuthentication(request, response, authentication);
		} catch (TokenException failed) {
			this.unsuccessfulAuthentication(request, response, failed);
			return;	// Do not continue the chain.
		}

		chain.doFilter(request, response);
	}

	protected Authentication attemptAuthentication(HttpServletRequest request,
	                                               HttpServletResponse response)
	        throws TokenMissingException {

		String token = null;

		try {
			token = this.authenticationParameterService.readToken(request);
		} catch (InvalidAuthStringException e) {
			throw new TokenMissingException("Header existed but was in a bad format.");
		}

		WebAuthenticationDetails details = this.authenticationDetailsSource.buildDetails(request);
		return this.delegate.performPreAuth(token, details);
	}

	protected void successfulAuthentication(HttpServletRequest request,
	                                        HttpServletResponse response,
	                                        Authentication authentication)
	        throws IOException,
	            ServletException {
		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (this.successHandler != null) {
			this.successHandler.onAuthenticationSuccess(request, response, authentication);
		}
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request,
	                                          HttpServletResponse response,
	                                          TokenException failed)
	        throws IOException,
	            ServletException {
		SecurityContextHolder.clearContext();

		if (this.failureHandler != null) {
			this.failureHandler.onAuthenticationFailure(request, response, failed);
		}
	}

}