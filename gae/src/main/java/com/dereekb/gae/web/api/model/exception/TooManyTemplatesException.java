package com.dereekb.gae.web.api.model.exception;

import java.util.List;

/**
 * Thrown when there are too many templates submitted in the request.
 * 
 * @author dereekb
 *
 */
public class TooManyTemplatesException extends ApiTooMuchInputException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_TITLE = "Too Many Templates";

	public TooManyTemplatesException(Integer max) {
		super(max);
	}

	public static void assertTemplatesCount(List<?> keys, Integer max) throws TooManyRequestKeysException {
		if (exceedsMaxCount(keys, max)) {
			throw new TooManyTemplatesException(max);
		}
	}
	
	// MARK: ApiResponseErrorConvertable
	@Override
	protected String getErrorTitle() {
		return ERROR_TITLE;
	}
	
	@Override
	public String toString() {
		return "TooManyTemplatesException [max=" + this.getMax() + "]";
	}
	
}
