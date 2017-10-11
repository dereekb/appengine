package com.dereekb.gae.utilities.collections.time.utility;

import java.util.Date;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;

/**
 * Wraps a {@link DatedTimeBlock} and provides utility functions.
 * 
 * @author dereekb
 *
 */
public interface DatedTimeBlockReadInstance
        extends TimeBlockReadInstance, DatedTimeBlockContainer {

	/**
	 * Returns the end of the block period.
	 * 
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getEndDate();

}
