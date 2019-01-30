package com.dereekb.gae.utilities.collections.time.utility;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;
import com.dereekb.gae.utilities.collections.time.utility.exception.TimeBlockNoOverlapException;

/**
 * Interface for performing operations between two {@link DatedTimeBlock}
 * values.
 * <p>
 * These operations should also function in cases where BlockB splits BlockA.
 * They will however only function as if the entire sequence ends at BlockB.
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
	 * Returns true if the two blocks are inline with eachother.
	 * 
	 * @return {@code true} if both blocks are inline.
	 */
	public boolean isInline();

	/**
	 * Returns the index of the first overlap.
	 * 
	 * @throws TimeBlockNoOverlapException
	 *             thrown if {@link #isContinuous()} is {@code false}.
	 */
	public Long getFirstConnectionIndex() throws TimeBlockNoOverlapException;

	/**
	 * Returns the union between the two blocks.
	 * <p>
	 * If {@link #isInline()} is false, then the union is rounded down to
	 * be within the set of times.
	 * 
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 * @throws TimeBlockNoOverlapException
	 *             thrown if {@link #isContinuous()} is {@code false}.
	 */
	public DatedTimeBlock getUnion() throws TimeBlockNoOverlapException;

	/**
	 * Returns the unique section in the first block.
	 * <p>
	 * If {@link #isInline()} is false, then
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
