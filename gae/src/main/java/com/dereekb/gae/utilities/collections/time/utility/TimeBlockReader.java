package com.dereekb.gae.utilities.collections.time.utility;

import java.util.Date;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;
import com.dereekb.gae.utilities.collections.time.TimeBlock;

/**
 * Used for reading the time between two different blocks.
 * 
 * @author dereekb
 *
 */
public interface TimeBlockReader {

	/**
	 * Returns the timeBlocks of time a single block represents in milliseconds.
	 * 
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getTimeInPeriod();

	/**
	 * Returns the timeBlocks of time in milliseconds for the input blocks.
	 * 
	 * @param timeBlocks
	 *            {@link TimeBlock}. Never {@code null}.
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getTimeForBlocks(TimeBlock timeBlock);

	/**
	 * Returns the timeBlocks of time in milliseconds for the input blocks.
	 * 
	 * @param timeBlocks
	 *            Number of blocks. Never {@code null}.
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getTimeForBlocks(Long timeBlocks);

	/**
	 * Rounds the end block to be inline with the start block.
	 * 
	 * @param unevenEndBlock
	 *            {@link Date}. Never {@code null}.
	 * @param inlineStartBlock
	 *            {@link Date}. Never {@code null}.
	 * @param rounding
	 *            {@link TimeBlockRounding} mode. Never {@code null}.
	 * @return
	 */
	public Date makeEndDateInlineWithDate(Date endDate,
	                                      Date startDate,
	                                      TimeBlockRounding rounding);

	/**
	 * Makes a new {@link DatedTimeBlock} between the two times.
	 * 
	 * @param start
	 *            {@link Date}. Never {@code null}.
	 * @param end
	 *            {@link Date}. Never {@code null}.
	 * @param rounding
	 *            {@link TimeBlockRounding} mode. Never {@code null}.
	 * @return {@link DatedTimeBlock}. Never {@code null}.
	 */
	public DatedTimeBlock makeDatedTimeBlockBetween(Date start,
	                                                Date end,
	                                                TimeBlockRounding rounding);

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
	public Long getBlocksForDates(Date start,
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
	public Long getBlocksForDateRange(TimeBlockDateRange range,
	                                  TimeBlockRounding rounding);

	/**
	 * Returns the end date of the input block with the number of blocks times
	 * 2, which covers the entire possible range of time values that the time
	 * block might cover or require.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}.
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getDateRangeEnd(DatedTimeBlock datedTimeBlock);

	/**
	 * Gets the end for the input.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}.
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getDateEnd(DatedTimeBlock datedTimeBlock);

	/**
	 * Returns the "end" of a timeBlocks of periods from a start date.
	 * 
	 * @param start
	 *            {@link Date}. Never {@code null}.
	 * @param timeBlocks
	 *            Number of blocks. Never {@code null}.
	 * 
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getDateEnd(Date start,
	                       Long timeBlocks);

	/**
	 * Returns true if both {@link DatedTimeBlock#getTimeBlockStart()} are
	 * "inline" with eachother.
	 *
	 * @param a
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @param b
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@code true} if both are inline with eachother.
	 */
	public boolean datesAreInline(DatedTimeBlock a,
	                              DatedTimeBlock b);

	/**
	 * Returns true if both dates are "inline" with eachother.
	 * <p>
	 * If the difference between the two is evenly divisible by
	 * {@link #getTimeInPeriod()}.
	 * 
	 * @param a
	 *            {@link Date}. Never {@code null}.
	 * @param b
	 *            {@link Date}. Never {@code null}.
	 * @return {@code true} if both are inline with eachother.
	 */
	public boolean datesAreInline(Date a,
	                              Date b);

	/**
	 * Creates a new {@link DatedTimeBlockUtilityInstance}.
	 * 
	 * @param datedTimeBlock
	 *            {@link DatedTimeBlock}. Never {@code null}.
	 * @return {@link DatedTimeBlockReadInstance}. Never {@code null}.
	 */
	public DatedTimeBlockUtilityInstance makeInstance(DatedTimeBlock datedTimeBlock);

}
