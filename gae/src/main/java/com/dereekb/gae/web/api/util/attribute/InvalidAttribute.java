package com.dereekb.gae.web.api.util.attribute;

import com.dereekb.gae.utilities.web.error.ErrorInfo;

/**
 * Attribute failure tuple to wrap which attribute failed updating, the invalid
 * value, and the details about why it failed.
 * 
 * @author dereekb
 *
 */
public interface InvalidAttribute {

	/**
	 * Returns the bad attribute.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAttribute();

	/**
	 * Returns the bad value.
	 * 
	 * @return {@link String} value of the bad value, or {@code null}.
	 */
	public String getValue();

	/**
	 * Returns the code for this attribute error, if available.
	 * 
	 * @return {@link String}. May be {@code null}.
	 */
	public String getCode();

	/**
	 * Returns details for this failure, if available.
	 * 
	 * @return {@link String}. May be {@code null}.
	 */
	public String getDetail();

	/**
	 * Returns an associated {@link ErrorInfo} with this attribute, if
	 * available.
	 * 
	 * @return {@link ErrorInfo}. May be {@code null}.
	 */
	public ErrorInfo getError();

}
