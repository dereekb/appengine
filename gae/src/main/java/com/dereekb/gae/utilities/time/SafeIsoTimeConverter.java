package com.dereekb.gae.utilities.time;

import java.util.Date;

/**
 * {@link IsoTimeConverter} that also has functions for safe conversions of null
 * values.
 * 
 * @author dereekb
 *
 */
public interface SafeIsoTimeConverter
        extends IsoTimeConverter {

	/**
	 * Converts an ISO8061 encoded string to a date.
	 * 
	 * @param isoString
	 *            {@link String}. Can be {@code null}.
	 * @return {@link Date}. {@code null} if null input.
	 */
	public Date safeConvertFromString(String isoString);

	/**
	 * Converts a date to a string.
	 * 
	 * @param date
	 *            {@link Date}. Can be {@code null}.
	 * @return {@link String}. {@code null} if null input.
	 */
	public String safeConvertToString(Date date);

}
