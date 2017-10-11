package com.dereekb.gae.utilities.collections.time.utility;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;

/**
 * Utility or container that has a {@link DatedTimeBlock}.
 * 
 * @author dereekb
 *
 */
public interface DatedTimeBlockContainer extends TimeBlockContainer {

	/**
	 * Returns the time block.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 */
	@Override
	public DatedTimeBlock getTimeBlock();

}
