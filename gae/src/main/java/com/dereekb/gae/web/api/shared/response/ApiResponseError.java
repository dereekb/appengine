package com.dereekb.gae.web.api.shared.response;

/**
 * Represents an error returned by the API.
 * <p>
 * Response implementations should expect to be serialized as-is, so they should
 * not contain any internal types that should not be exposed outside the system.
 *
 * @author dereekb
 * @see {@link ApiResponse}
 */
public interface ApiResponseError {

	/**
	 * Returns the error code.
	 *
	 * @return error code. Never {@code null}.
	 */
	public String getErrorCode();

	/**
	 * Returns the error title.
	 *
	 * @return error title. Never {@code null}.
	 */
	public String getErrorTitle();

	/**
	 * Returns details about the error.
	 *
	 * @return details about the error, or {@code null} if not applicable.
	 */
	public String getErrorDetail();

	/**
	 * Returns any data specific to the error, if available.
	 *
	 * @return {@link Object} containing data, or {@code null} if not
	 *         applicable.
	 */
	public Object getErrorData();

}
