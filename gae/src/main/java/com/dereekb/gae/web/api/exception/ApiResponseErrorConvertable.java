package com.dereekb.gae.web.api.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * Object is convertable to an {@link ApiResponseError}.
 * 
 * @author dereekb
 *
 */
public interface ApiResponseErrorConvertable {

	/**
	 * Builds an response error.
	 * 
	 * @return {@link ApiResponseError}.
	 */
	public ApiResponseError asResponseError();

}
