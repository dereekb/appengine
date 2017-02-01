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

/**
 * Authentication filter for filtering security by using a {@link LoginToken}.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFilter extends GenericFilterBean {

	public static final String DEFAULT_BEARER_PREFIX = "Bearer ";
	public static final String DEFAULT_HEADER_STRING = "Authorization";

	private String headerString = DEFAULT_HEADER_STRING;
	private String bearerPrefix = DEFAULT_BEARER_PREFIX;

	private int bearerPrefixLength = DEFAULT_BEARER_PREFIX.length();

	private final WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private AuthenticationSuccessHandler successHandler;
	private AuthenticationFailureHandler failureHandler;

	private LoginTokenAuthenticationFilterDelegate delegate;

	public LoginTokenAuthenticationFilter() {}

	public LoginTokenAuthenticationFilter(LoginTokenAuthenticationFilterDelegate delegate,
	        AuthenticationSuccessHandler successHandler,
	        AuthenticationFailureHandler failureHandler) throws IllegalArgumentException {
		this.setDelegate(delegate);
		this.setSuccessHandler(successHandler);
		this.setFailureHandler(failureHandler);
	}

	public String getHeaderString() {
		return this.headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	public String getBearerPrefix() {
		return this.bearerPrefix;
	}

	public void setBearerPrefix(String bearerPrefix) throws IllegalArgumentException {
		if (bearerPrefix == null) {
			throw new IllegalArgumentException("Bearer prefix cannot be null.");
		}

		this.bearerPrefix = bearerPrefix;
		this.bearerPrefixLength = this.bearerPrefix.length();
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

		String header = request.getHeader(this.headerString);

		if (header == null || !header.startsWith(this.bearerPrefix)) {
			throw new TokenMissingException();
		}

		String token = header.substring(this.bearerPrefixLength);
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

	public static String buildTokenHeader(String token) {
		if (token == null) {
			throw new IllegalArgumentException("Token cannot be null.");
		}

		return LoginTokenAuthenticationFilter.DEFAULT_BEARER_PREFIX + token;
	}

}