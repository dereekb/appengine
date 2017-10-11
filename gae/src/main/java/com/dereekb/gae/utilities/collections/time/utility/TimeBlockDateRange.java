package com.dereekb.gae.utilities.collections.time.utility;

import java.util.Date;

import com.dereekb.gae.utilities.collections.time.TimeBlock;

/**
 * Range of dates. Used by {@link TimeBlock} related utilities.
 * 
 * @author dereekb
 *
 */
public interface TimeBlockDateRange {

	/**
	 * Returns the time the block starts.
	 * 
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getTimeBlockStart();

	/**
	 * Returns the time the block ends.
	 * 
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getTimeBlockEnd();

}
