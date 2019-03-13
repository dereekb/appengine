package com.dereekb.gae.utilities.time;

import java.util.Date;

/**
 * Used for converting ISO80601 times to/from a string.
 * 
 * @author dereekb
 *
 */
public interface IsoTimeConverter {

	/**
	 * Converts an ISO8061 encoded string to a date.
	 * 
	 * @param isoString
	 *            {@link String}. Never {@code null}.
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date convertFromString(String isoString);

	/**
	 * Converts a date to a string.
	 * 
	 * @param date
	 *            {@link Date}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String convertToString(Date date);

}
