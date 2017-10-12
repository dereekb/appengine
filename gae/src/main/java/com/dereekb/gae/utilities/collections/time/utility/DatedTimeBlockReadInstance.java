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

	/**
	 * Whether or not this can be split by the input.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@code true} if split.
	 */
	public boolean isSplitBy(DatedTimeBlock datedTimeBlock);

	/**
	 * Whether or not this overlaps with the input.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@code true} if split.
	 */
	public boolean overlapsWith(DatedTimeBlock datedTimeBlock);

	/**
	 * Whether or not this is continuous or overlaps with the input.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@code true} if split.
	 */
	public boolean continuousWith(DatedTimeBlock datedTimeBlock);

	/**
	 * Whether or not the two blocks are on the same block scale.
	 * <p>
	 * I.E. Date(0) and Date(500) with a time period of 500L would return true.
	 * Using "Date(0) and Date(499) with thte same time period would return
	 * false.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@code true} if both inline with eachother.
	 */
	public boolean isInlineWith(DatedTimeBlock datedTimeBlock);

}
