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
import com.dereekb.gae.server.auth.security.token.exception.TokenHeaderMissingException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;

/**
 * Authentication filter for filtering security by using a {@link LoginToken}.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFilter extends GenericFilterBean {

	private static final String DEFAULT_BEARER_PREFIX = "Bearer ";
	private static final String DEFAULT_HEADER_STRING = "Authorization";

	private String headerString = DEFAULT_HEADER_STRING;
	private String bearerPrefix = DEFAULT_BEARER_PREFIX;

	private int bearerPrefixLength = DEFAULT_BEARER_PREFIX.length();

	private LoginTokenAuthenticationProvider loginTokenAuthenticationProvider;
	private WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private AuthenticationSuccessHandler successHandler;
	private AuthenticationFailureHandler failureHandler;

	public LoginTokenAuthenticationFilter() {}

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

	public LoginTokenAuthenticationProvider getLoginTokenAuthenticationProvider() {
		return this.loginTokenAuthenticationProvider;
	}

	public void setLoginTokenAuthenticationProvider(LoginTokenAuthenticationProvider loginTokenAuthenticationProvider) {
		this.loginTokenAuthenticationProvider = loginTokenAuthenticationProvider;
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

	// MARK: GenericFilterBean
	@Override
	public void doFilter(ServletRequest req,
	                     ServletResponse res,
	                     FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		LoginTokenAuthentication authentication;

		try {
			authentication = this.attemptAuthentication(request, response);
		} catch (TokenException failed) {
			this.unsuccessfulAuthentication(request, response, failed);
			return;
		}

		this.successfulAuthentication(request, response, authentication);

		chain.doFilter(request, response);
	}

	protected LoginTokenAuthentication attemptAuthentication(HttpServletRequest request,
	                                                         HttpServletResponse response)
	        throws TokenHeaderMissingException {

		String header = request.getHeader(this.headerString);

		if (header == null || !header.startsWith(this.bearerPrefix)) {
			throw new TokenHeaderMissingException();
		}

		String token = header.substring(this.bearerPrefixLength);
		WebAuthenticationDetails details = this.authenticationDetailsSource.buildDetails(request);
		return this.loginTokenAuthenticationProvider.authenticate(token, details);
	}

	protected void successfulAuthentication(HttpServletRequest request,
	                                        HttpServletResponse response,
	                                        Authentication authentication) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (this.successHandler != null) {
			this.successHandler.onAuthenticationSuccess(request, response, authentication);
		}
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request,
	                                          HttpServletResponse response,
	                                          TokenException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		if (this.failureHandler != null) {
			this.failureHandler.onAuthenticationFailure(request, response, failed);
		}
	}

}