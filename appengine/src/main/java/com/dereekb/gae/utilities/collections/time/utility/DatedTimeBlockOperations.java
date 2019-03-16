package com.dereekb.gae.utilities.collections.time.utility;

import java.util.List;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;
import com.dereekb.gae.utilities.collections.time.utility.exception.TimeBlockNoOverlapException;

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
	 * @throws IllegalArgumentException
	 *             thrown if the input block is before the start of the
	 *             contained block.
	 */
	public DatedTimeBlockSetOperations operatorWith(DatedTimeBlock datedTimeBlock) throws IllegalArgumentException;

	/**
	 * Splits the contained block with the input.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * @throws TimeBlockNoOverlapException
	 *             thrown if the two times do not overlap.
	 * @throws IllegalArgumentException
	 *             thrown if the input block is before the start of the
	 *             contained block.
	 */
	public List<DatedTimeBlock> split(DatedTimeBlock datedTimeBlock)
	        throws IllegalArgumentException,
	            TimeBlockNoOverlapException;

}
