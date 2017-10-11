package com.dereekb.gae.utilities.collections.time.utility;

/**
 * Utility for converting arbitrary amounts of time in milliseconds to blocks.
 * 
 * @author dereekb
 *
 */
public interface TimeBlockConverter {

	/**
	 * Returns the amount of time in an integer.
	 * 
	 * @param time
	 *            Time in milliseconds. Never {@code null}.
	 * @param timeInPeriod
	 *            Time in a period.
	 * @return {@link Integer}. Never {@codee null}.
	 */
	public Integer roundBlockSize(Long time,
	                              Long timeInPeriod);

}
