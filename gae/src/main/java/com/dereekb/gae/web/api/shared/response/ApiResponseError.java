package com.dereekb.gae.web.api.shared.response;

import com.dereekb.gae.utilities.web.error.ErrorInfo;

/**
 * Represents an error returned by the API.
 * <p>
 * Response implementations should expect to be serialized as-is, so they should
 * not contain any internal types that should not be exposed outside the system.
 *
 * @author dereekb
 * @see {@link ApiResponse}
 */
public interface ApiResponseError
        extends ErrorInfo {

	/**
	 * Returns any data specific to the error, if available.
	 *
	 * @return {@link Object} containing data, or {@code null} if not
	 *         applicable.
	 */
	public Object getErrorData();

}
