package com.dereekb.gae.server.app.model.app.info.exception;

import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception thrown when comparing {@link AppServiceVersionInfo}.
 *
 * @author dereekb
 *
 */
public class AppInequalityException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String INEQUALITY_ERROR_CODE = "APP_INEQUALITY";
	public static final String INEQUALITY_ERROR_TITLE = "App Inequality";

	public AppInequalityException() {
		super();
	}

	public AppInequalityException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AppInequalityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppInequalityException(String message) {
		super(message);
	}

	public AppInequalityException(Throwable cause) {
		super(cause);
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(INEQUALITY_ERROR_CODE, INEQUALITY_ERROR_TITLE, this.getMessage());
	}

	@Override
	public String toString() {
		return "AppInequalityException []";
	}

}
