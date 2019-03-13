package com.dereekb.gae.web.api.exception.impl;

import java.util.List;

import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.exception.utility.ApiResponseErrorConvertableUtility;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * {@link ApiResponseErrorConvertable} that wraps several other errors.
 * 
 * @author dereekb
 *
 */
public class MultiApiResponseErrorConvertable
        implements ApiResponseErrorConvertable {

	public static final String ERROR_CODE = "MULTI_ERRORS";

	private List<? extends ApiResponseErrorConvertable> errors;

	protected MultiApiResponseErrorConvertable(List<? extends ApiResponseErrorConvertable> errors) {
		super();
		this.setErrors(errors);
	}

	/**
	 * Wraps the input list into a single {@link ApiResponseErrorConvertable}.
	 * 
	 * @param errors
	 *            {@link List}. Never {@code null}.
	 * @return {@link ApiResponseErrorConvertable}, or {@code null} if the input
	 *         list is empty.
	 */
	public static ApiResponseErrorConvertable wrap(List<ApiResponseErrorConvertable> errors) {
		if (errors.isEmpty()) {
			return null;
		} else if (errors.size() > 1) {
			return new MultiApiResponseErrorConvertable(errors);
		} else {
			return errors.get(0);
		}
	}

	public List<? extends ApiResponseErrorConvertable> getErrors() {
		return this.errors;
	}

	public void setErrors(List<? extends ApiResponseErrorConvertable> errors) {
		if (errors == null) {
			throw new IllegalArgumentException("errors cannot be null.");
		}

		this.errors = errors;
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseError asResponseError() {
		List<ApiResponseError> data = ApiResponseErrorConvertableUtility.asResponseErrors(this.errors);
		return new ApiResponseErrorImpl(ERROR_CODE, data);
	}

	@Override
	public String toString() {
		return "MultiApiResponseErrorConvertable [errors=" + this.errors + "]";
	}

}
