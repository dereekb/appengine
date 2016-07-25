package com.dereekb.gae.server.auth.security.token.filter.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.handler.ApiTokenExceptionHandler;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link AuthenticationFailureHandler} implementation for the system.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

	private ApiTokenExceptionHandler handler;
	private ObjectMapper mapper = new ObjectMapper();

	public LoginTokenAuthenticationFailureHandler(ApiTokenExceptionHandler handler) {
		this.setHandler(handler);
	}

	public ApiTokenExceptionHandler getHandler() {
		return this.handler;
	}

	public void setHandler(ApiTokenExceptionHandler handler) {
		this.handler = handler;
	}

	public ObjectMapper getMapper() {
		return this.mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
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

			response.setContentType("application/json");

			ServletOutputStream outputStream = response.getOutputStream();
			this.mapper.writeValue(outputStream, apiResponse);

			// mapper.writeValue(response.getOutputStream()

		} else {

		}
	}

}
