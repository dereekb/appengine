package com.dereekb.gae.server.auth.security.token.filter.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.dereekb.gae.server.auth.security.misc.AbstractResponseHandler;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.handler.ApiTokenExceptionHandler;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * {@link AuthenticationFailureHandler} implementation for the system.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFailureHandler extends AbstractResponseHandler
        implements AuthenticationFailureHandler {

	private ApiTokenExceptionHandler handler;

	public LoginTokenAuthenticationFailureHandler(ApiTokenExceptionHandler handler) {
		this.setHandler(handler);
	}

	public ApiTokenExceptionHandler getHandler() {
		return this.handler;
	}

	public void setHandler(ApiTokenExceptionHandler handler) {
		this.handler = handler;
	}

	// MARK: AuthenticationFailureHandler
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    AuthenticationException exception) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		if (TokenException.class.isAssignableFrom(exception.getClass())) {
			TokenException tokenException = (TokenException) exception;
			ApiResponseImpl apiResponse = this.handler.handleException(tokenException);
			this.writeJsonResponse(response, apiResponse);
		}
	}

}
