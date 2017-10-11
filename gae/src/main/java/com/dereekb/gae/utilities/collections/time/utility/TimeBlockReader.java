package com.dereekb.gae.utilities.collections.time.utility;

import java.util.Date;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;

/**
 * Used for reading the time between two different blocks.
 * 
 * @author dereekb
 *
 */
public interface TimeBlockReader {

	/**
	 * Returns the length of time a single block represents in milliseconds.
	 * 
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getPeriodTime();

	/**
	 * Returns the length of time in milliseconds for the input blocks.
	 * 
	 * @param length
	 *            Number of blocks. Never {@code null}.
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getTimeForBlocks(Integer length);

	/**
	 * Returns the number of blocks between the start and end.
	 * 
	 * @param start
	 *            {@link Date}. Never {@code null}.
	 * @param end
	 *            {@link Date}. Never {@code null}.
	 * @param rounding
	 *            {@link TimeBlockRounding} mode. Never {@code null}.
	 * @return {@link Integer}. Never {@code null}.
	 */
	public Integer getBlocksForDates(Date start,
	                                 Date end,
	                                 TimeBlockRounding rounding);

	/**
	 * Returns the number of blocks in the {@link TimeBlockDateRange}.
	 * 
	 * @param range
	 *            {@link TimeBlockDateRange}. Never {@code null}.
	 * @param rounding
	 *            {@link TimeBlockRounding}. Never {@code null}.
	 * @return {@link Integer}. Never {@code null}.
	 */
	public Integer getBlocksForDateRange(TimeBlockDateRange range,
	                                     TimeBlockRounding rounding);

	/**
	 * Gets the end for the input.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}.
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getDateEnd(DatedTimeBlock datedTimeBlock);

	/**
	 * Returns the "end" of a length of periods from a start date.
	 * 
	 * @param start
	 *            {@link Date}. Never {@code null}.
	 * @param length
	 *            Number of blocks. Never {@code null}.
	 * 
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getDateEnd(Date start,
	                       Integer length);

	/**
	 * Creates a new {@link DatedTimeBlockReadInstance}.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@link DatedTimeBlockReadInstance}. Never {@code null}.
	 */
	public DatedTimeBlockReadInstance makeInstance(DatedTimeBlock datedTimeBlock);

}
