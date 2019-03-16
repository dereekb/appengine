package com.dereekb.gae.utilities.collections.time.utility;

import com.dereekb.gae.utilities.collections.time.TimeBlock;

/**
 * Utility or container that has a {@link TimeBlock}.
 * 
 * @author dereekb
 *
 */
public interface TimeBlockContainer {

	/**
	 * Returns the time block.
	 * 
	 * @return {@link TimeBlock}. Never {@code null}.
	 */
	public TimeBlock getTimeBlock();

}
