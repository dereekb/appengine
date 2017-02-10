package com.dereekb.gae.utilities.web.error;

/**
 * Generic class for error info.
 * 
 * @author dereekb
 *
 */
public interface ErrorInfo {

	/**
	 * Returns the error code.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getErrorCode();

	/**
	 * Returns the error title.
	 *
	 * @return {@link String}, or {@code null} if not available.
	 */
	public String getErrorTitle();

	/**
	 * Returns details about the error.
	 *
	 * @return {@link String}, or {@code null} if not available.
	 */
	public String getErrorDetail();

}
