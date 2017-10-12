package com.dereekb.gae.utilities.collections.time.utility;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;

/**
 * Interface for providing operations on an input Time Block.
 * 
 * @author dereekb
 *
 */
public interface DatedTimeBlockOperations
        extends DatedTimeBlockContainer {

	/**
	 * Creates a new operator with the input block.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@link DatedTimeBlockSetOperations}. Never {@code null}.
	 */
	public DatedTimeBlockSetOperations operatorWith(DatedTimeBlock datedTimeBlock);

}
