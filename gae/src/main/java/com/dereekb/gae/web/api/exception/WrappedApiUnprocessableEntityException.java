package com.dereekb.gae.web.api.exception;

/**
 * {@link WrappedApiErrorException} used for HTTP Unprocessable Entity
 * responses.
 * 
 * @author dereekb
 *
 */
public class WrappedApiUnprocessableEntityException extends WrappedApiErrorException {

	private static final long serialVersionUID = 1L;

	public WrappedApiUnprocessableEntityException(ApiResponseErrorConvertable error) {
		super(error);
	}

}
