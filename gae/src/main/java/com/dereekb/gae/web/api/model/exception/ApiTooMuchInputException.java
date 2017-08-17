package com.dereekb.gae.web.api.model.exception;

import java.util.Collection;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when too much input is provided.
 * 
 * @author dereekb
 *
 */
public class ApiTooMuchInputException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "TOO_MUCH_INPUT";
	public static final String ERROR_TITLE = "Too Much Input";

	private Integer max;

	public ApiTooMuchInputException(Integer max) {
		this.max = max;
	}

	protected static boolean exceedsMaxCount(Collection<?> keys, Integer max) throws TooManyRequestKeysException {
		return (max != null && keys.size() > max);
	}
	
	public Integer getMax() {
		return this.max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	public final ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, this.getErrorTitle());
	
		String message = "Too much input was provided in the request.";
		
		if (this.max != null) {
			message = " Max of " + this.max + " elements is allowed.";
		}
		
		error.setDetail(message);
		error.setData(this.max);
		
		return error;
	}
	
	protected String getErrorTitle() {
		return ERROR_TITLE;
	}
	
	@Override
	public String toString() {
		return "TooManyRequestKeysException [max=" + this.max + "]";
	}

}
