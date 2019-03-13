package com.dereekb.gae.server.auth.security.misc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Acts as both controller advice and a {@link AccessDeniedHandler}
 * implementation for handling {@link AccessDeniedException} exceptions.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccessDeniedHandlerImpl extends AbstractResponseHandler
        implements AccessDeniedHandler {

	public static final String DEFAULT_ERROR_CODE = "ACCESS_DENIED";
	public static final String DEFAULT_ERROR_TITLE = "Access Denied";

	private String errorCode = DEFAULT_ERROR_CODE;
	private String errorTitle = DEFAULT_ERROR_TITLE;

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorTitle() {
		return this.errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

	// MARK: AccessDeniedHandler
	@Override
	public void handle(HttpServletRequest request,
	                   HttpServletResponse response,
	                   AccessDeniedException accessDeniedException)
	        throws IOException,
	            ServletException {

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		ApiResponseImpl apiResponse = this.buildErrorResponse(accessDeniedException);
		this.writeJsonResponse(response, apiResponse);
	}

	private ApiResponseImpl buildErrorResponse(AccessDeniedException accessDeniedException) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl(this.errorCode, this.errorTitle);
		response.setError(error);

		return response;
	}

	@Override
	public String toString() {
		return "AccessDeniedHandlerImpl [errorCode=" + this.errorCode + ", errorTitle=" + this.errorTitle + "]";
	}

}
