package com.dereekb.gae.utilities.collections.time.utility;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;
import com.dereekb.gae.utilities.collections.time.utility.exception.TimeBlockOperationException;

/**
 * Interface for performing operations between two {@link DatedTimeBlock}
 * values.
 * 
 * @author dereekb
 *
 */
public interface TimeBlockSetOperations {

	/**
	 * Returns the first time block.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 */
	public DatedTimeBlock getBlockA();

	/**
	 * Returns the second time block.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 */
	public DatedTimeBlock getBlockB();

	/**
	 * Returns true if the two blocks overlap eachother.
	 * 
	 * @return {@link 
	 */
	public boolean hasOverlap();

	/**
	 * Returns the union between the two blocks.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 * @throws TimeBlockOperationException
	 *             thrown if the two blocks do not intersect.
	 */
	public DatedTimeBlock getUnion() throws TimeBlockOperationException;

}
