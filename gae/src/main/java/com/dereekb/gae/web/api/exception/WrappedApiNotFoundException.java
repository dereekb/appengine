package com.dereekb.gae.web.api.exception;

/**
 * {@link WrappedApiErrorException} used for HTTP Not Found responses.
 *
 * @author dereekb
 *
 */
public class WrappedApiNotFoundException extends WrappedApiErrorException {

	private static final long serialVersionUID = 1L;

	public WrappedApiNotFoundException(ApiResponseErrorConvertable error) {
		super(error);
	}

}
