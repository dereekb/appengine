package com.dereekb.gae.utilities.web.error;

import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Generic class for error info.
 * <p>
 * Is {@link AlwaysKeyed} by error code.
 * 
 * @author dereekb
 *
 */
public interface ErrorInfo
        extends AlwaysKeyed<String> {

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
