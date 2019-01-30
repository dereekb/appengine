package com.dereekb.gae.web.api.exception;

/**
 * {@link WrappedApiErrorException} used for HTTP Bad Request responses.
 * 
 * @author dereekb
 *
 */
public class WrappedApiBadRequestException extends WrappedApiErrorException {

	private static final long serialVersionUID = 1L;

	public WrappedApiBadRequestException(ApiResponseErrorConvertable error) {
		super(error);
	}

}
