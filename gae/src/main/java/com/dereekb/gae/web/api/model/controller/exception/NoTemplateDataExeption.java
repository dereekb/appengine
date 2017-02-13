package com.dereekb.gae.web.api.model.controller.exception;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Used for edit model requests that require template date, such as create/edit.
 * 
 * @author dereekb
 *
 */
public class NoTemplateDataExeption extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "MISSING_TEMPLATE_DATE";
	public static final String ERROR_TITLE = "Missing Templates";
	public static final String ERROR_DETAIL = "Required template data was missing.";

	public NoTemplateDataExeption() {
		super();
	}

	public NoTemplateDataExeption(String message) {
		super(message);
	}

	public NoTemplateDataExeption(Throwable cause) {
		super(cause);
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, ERROR_DETAIL);
	}

	@Override
	public String toString() {
		return "NoTemplateDataExeption []";
	}

}
