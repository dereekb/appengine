package com.dereekb.gae.utilities.collections.time.utility;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;
import com.dereekb.gae.utilities.collections.time.utility.exception.TimeBlockNoOverlapException;

/**
 * Interface for performing operations between two {@link DatedTimeBlock}
 * values.
 * 
 * @author dereekb
 *
 */
public interface DatedTimeBlockSetOperations {

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
	 * Returns true if the two blocks overlap each other or are continuous.
	 * 
	 * @return {@code true} if the blocks are continuous or overlap.
	 */
	public boolean isContinuous();

	/**
	 * Returns true if the two blocks overlap each other.
	 * 
	 * @return {@code true} if there is overlap with the other block.
	 */
	public boolean hasOverlap();

	/**
	 * Returns the index of the first overlap.
	 * @throws TimeBlockNoOverlapException
	 *             thrown if {@link #isContinuous()} is {@code false}.
	 */
	public Long getFirstOverlapIndex() throws TimeBlockNoOverlapException;

	/**
	 * Returns the union between the two blocks.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 * @throws TimeBlockNoOverlapException
	 *             thrown if {@link #isContinuous()} is {@code false}.
	 */
	public DatedTimeBlock getUnion() throws TimeBlockNoOverlapException;

	/**
	 * Returns the unique section in the first block.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 */
	public DatedTimeBlock getUnique();

	/**
	 * Returns the intersection between the two blocks.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 * @throws TimeBlockNoOverlapException
	 *             thrown if {@link #isContinuous()} is {@code false}.
	 */
	public DatedTimeBlock getIntersection() throws TimeBlockNoOverlapException;

	/**
	 * Returns the compliment between two blocks.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 * @throws TimeBlockNoOverlapException
	 *             thrown if {@link #isContinuous()} is {@code false}.
	 */
	public DatedTimeBlock getCompliment() throws TimeBlockNoOverlapException;

}
