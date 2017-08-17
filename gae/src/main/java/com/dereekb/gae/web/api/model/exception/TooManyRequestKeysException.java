package com.dereekb.gae.web.api.model.exception;

import java.util.List;

/**
 * Thrown when there are too many keys requested.
 * 
 * @author dereekb
 *
 */
public class TooManyRequestKeysException extends ApiTooMuchInputException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_TITLE = "Too Many Keys";

	public TooManyRequestKeysException(Integer max) {
		super(max);
	}

	public static void assertKeysCount(List<String> keys, Integer max) throws TooManyRequestKeysException {
		if (exceedsMaxCount(keys, max)) {
			throw new TooManyRequestKeysException(max);
		}
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	protected String getErrorTitle() {
		return ERROR_TITLE;
	}
	
	@Override
	public String toString() {
		return "TooManyRequestKeysException [max=" + this.getMax() + "]";
	}

}
