package com.dereekb.gae.utilities.collections.time;

import java.util.Date;

/**
 * {@link TimeBlock} that has a set start time.
 * 
 * @author dereekb
 *
 */
public interface DatedTimeBlock
        extends TimeBlock {

	/**
	 * Returns the time the block starts.
	 * 
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getTimeBlockStart();

}
