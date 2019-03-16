package com.dereekb.gae.utilities.collections.time.utility.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;
import com.dereekb.gae.utilities.collections.time.TimeBlock;
import com.dereekb.gae.utilities.collections.time.utility.DatedTimeBlockReadInstance;
import com.dereekb.gae.utilities.collections.time.utility.DatedTimeBlockSetOperations;
import com.dereekb.gae.utilities.collections.time.utility.DatedTimeBlockUtilityInstance;
import com.dereekb.gae.utilities.collections.time.utility.TimeBlockDateRange;
import com.dereekb.gae.utilities.collections.time.utility.TimeBlockReadInstance;
import com.dereekb.gae.utilities.collections.time.utility.TimeBlockReader;
import com.dereekb.gae.utilities.collections.time.utility.TimeBlockRounding;
import com.dereekb.gae.utilities.collections.time.utility.exception.TimeBlockNoOverlapException;
import com.dereekb.gae.utilities.collections.time.utility.exception.TimeBlockNotContinuousException;

/**
 * {@link TimeBlockReader} implementation.
 * 
 * @author dereekb
 *
 */
public class TimeBlockReaderImpl
        implements TimeBlockReader {

	private Long timeInPeriod;

	public TimeBlockReaderImpl(Long timeInPeriod) throws IllegalArgumentException {
		this.setTimeInPeriod(timeInPeriod);
	}

	public void setTimeInPeriod(Long timeInPeriod) {
		if (timeInPeriod == null) {
			throw new IllegalArgumentException("timeInPeriod cannot be null.");
		}

		this.timeInPeriod = timeInPeriod;
	}

	// MARK: TimeBlockReader
	@Override
	public Long getTimeInPeriod() {
		return this.timeInPeriod;
	}

	@Override
	public Long getTimeForBlocks(TimeBlock timeBlock) {
		return this.getTimeForBlocks(timeBlock.getTimeBlocks());
	}

	@Override
	public Long getTimeForBlocks(Long timeBlocks) {
		return this.timeInPeriod * timeBlocks;
	}

	@Override
	public Date makeEndDateInlineWithDate(Date endDate,
	                                      Date startDate,
	                                      TimeBlockRounding rounding) {
		DatedTimeBlockImpl datedTimeBlock = this.makeDatedTimeBlockBetween(startDate, endDate, rounding);
		return this.getDateEnd(datedTimeBlock);
	}

	@Override
	public DatedTimeBlockImpl makeDatedTimeBlockBetween(Date start,
	                                                    Date end,
	                                                    TimeBlockRounding rounding) {
		if (start.after(end)) {
			throw new IllegalArgumentException("Start should not be after end.");
		}

		Long blocks = this.getBlocksForDates(start, end, rounding);
		return new DatedTimeBlockImpl(blocks, start);
	}

	@Override
	public Long getBlocksForDateRange(TimeBlockDateRange range,
	                                  TimeBlockRounding rounding) {
		return this.getBlocksForDates(range.getTimeBlockStart(), range.getTimeBlockEnd(), rounding);
	}

	@Override
	public Long getBlocksForDates(Date start,
	                              Date end,
	                              TimeBlockRounding rounding) {
		return rounding.roundBlockSize(end.getTime() - start.getTime(), this.timeInPeriod);
	}

	@Override
	public Date getDateRangeEnd(DatedTimeBlock datedTimeBlock) {
		return this.getDateEnd(datedTimeBlock.getTimeBlockStart(), datedTimeBlock.getTimeBlocks() * 2);
	}

	@Override
	public Date getDateEnd(DatedTimeBlock datedTimeBlock) {
		Date start = datedTimeBlock.getTimeBlockStart();
		Long timeBlocks = datedTimeBlock.getTimeBlocks();
		return this.getDateEnd(start, timeBlocks);
	}

	@Override
	public Date getDateEnd(Date start,
	                       Long timeBlocks) {
		Long timeForBlocks = this.getTimeForBlocks(timeBlocks);
		return new Date(start.getTime() + timeForBlocks);
	}

	@Override
	public boolean datesAreInline(DatedTimeBlock a,
	                              DatedTimeBlock b) {
		return this.datesAreInline(a.getTimeBlockStart(), b.getTimeBlockStart());
	}

	@Override
	public boolean datesAreInline(Date a,
	                              Date b) {
		Long difference = Math.abs(b.getTime() - a.getTime());
		return (difference % this.timeInPeriod) == 0;
	}

	@Override
	public DatedTimeBlockUtilityInstance makeInstance(DatedTimeBlock datedTimeBlock) {
		if (datedTimeBlock == null) {
			throw new IllegalArgumentException();
		}

		return new DatedTimeBlockUtilityInstanceImpl(datedTimeBlock);
	}

	/**
	 * {@link TimeBlockReadInstance} implementation.
	 * 
	 * @author dereekb
	 *
	 */
	private class TimeBlockReadInstanceImpl
	        implements TimeBlockReadInstance {

		private TimeBlock timeBlock;

		public TimeBlockReadInstanceImpl(TimeBlock timeBlock) {
			this.timeBlock = timeBlock;
		}

		// MARK: TimeBlockReadInstance
		@Override
		public TimeBlock getTimeBlock() {
			return this.timeBlock;
		}

		@Override
		public Long getTotalTime() {
			return TimeBlockReaderImpl.this.getTimeForBlocks(this.timeBlock);
		}

	}

	/**
	 * {@link DatedTimeBlockReadInstance} implementation.
	 * 
	 * @author dereekb
	 *
	 */
	private class DatedTimeBlockUtilityInstanceImpl extends TimeBlockReadInstanceImpl
	        implements DatedTimeBlockUtilityInstance {

		private DatedTimeBlock datedTimeBlock;
		private transient Date endDate;

		public DatedTimeBlockUtilityInstanceImpl(DatedTimeBlock datedTimeBlock) {
			super(datedTimeBlock);
			this.datedTimeBlock = datedTimeBlock;
		}

		// MARK: DatedTimeBlockUtilityInstance
		@Override
		public DatedTimeBlock getTimeBlock() {
			return this.datedTimeBlock;
		}

		@Override
		public Date getEndDate() {
			if (this.endDate == null) {
				this.endDate = TimeBlockReaderImpl.this.getDateEnd(this.datedTimeBlock);
			}

			return this.endDate;
		}

		@Override
		public DatedTimeBlockSetOperationsImpl operatorWith(DatedTimeBlock datedTimeBlock) {
			if (datedTimeBlock.getTimeBlockStart().before(this.datedTimeBlock.getTimeBlockStart())) {
				throw new IllegalArgumentException("Input date was before this date.");
			}

			return new DatedTimeBlockSetOperationsImpl(datedTimeBlock);
		}

		@Override
		public List<DatedTimeBlock> split(DatedTimeBlock splitBlock)
		        throws IllegalArgumentException,
		            TimeBlockNoOverlapException {

			DatedTimeBlockSetOperationsImpl operations = this.operatorWith(splitBlock);
			operations.assertHasOverlap();

			DatedTimeBlock unique = operations.getUnique();

			DatedTimeBlock timeBlock = this.getTimeBlock();

			Date timeBlockStart = timeBlock.getTimeBlockStart();
			Date splitBlockEndDate = TimeBlockReaderImpl.this.getDateEnd(splitBlock);

			Date endBlockStartDate = TimeBlockReaderImpl.this.makeEndDateInlineWithDate(splitBlockEndDate,
			        timeBlockStart, TimeBlockRounding.CEIL);
			Date endBlockEndDate = this.getEndDate();

			// Blocks from the intersection start to the end.
			DatedTimeBlockImpl endBlock = TimeBlockReaderImpl.this.makeDatedTimeBlockBetween(endBlockStartDate,
			        endBlockEndDate, TimeBlockRounding.FLOOR);

			List<DatedTimeBlock> split = new ArrayList<DatedTimeBlock>();

			if (unique.getTimeBlocks() > 0) {
				split.add(unique);
			}

			split.add(splitBlock);

			if (endBlock.getTimeBlocks() > 0) {
				split.add(endBlock);
			}

			return split;
		}

		@Override
		public boolean isSplitBy(DatedTimeBlock datedTimeBlock) {
			if (this.overlapsWith(datedTimeBlock)) {
				Date endDate = TimeBlockReaderImpl.this.getDateEnd(datedTimeBlock);
				return (endDate.compareTo(this.getEndDate()) <= 0);
			}

			return false;
		}

		@Override
		public boolean overlapsWith(DatedTimeBlock datedTimeBlock) {
			return this.compareBlockContinuity(datedTimeBlock) < 0;
		}

		@Override
		public boolean continuousWith(DatedTimeBlock datedTimeBlock) {
			return this.compareBlockContinuity(datedTimeBlock) <= 0;
		}

		@Override
		public boolean isInlineWith(DatedTimeBlock datedTimeBlock) {
			return TimeBlockReaderImpl.this.datesAreInline(this.getTimeBlock(), datedTimeBlock);
		}

		private Integer compareBlockContinuity(DatedTimeBlock datedTimeBlock) {
			return datedTimeBlock.getTimeBlockStart().compareTo(this.getEndDate());
		}

		private class DatedTimeBlockSetOperationsImpl
		        implements DatedTimeBlockSetOperations {

			private DatedTimeBlock blockB;
			private transient Boolean isContinuous;
			private transient Boolean hasOverlap;
			private transient Boolean isInline;
			private transient Boolean isLargerThanBlockB;
			private transient Long firstOverlap;
			private transient Date firstOverlapDate;
			private transient Date blockBEndDate;
			private transient DatedTimeBlock union;
			private transient DatedTimeBlock unique;
			private transient DatedTimeBlock intersection;
			private transient DatedTimeBlock compliment;

			public DatedTimeBlockSetOperationsImpl(DatedTimeBlock blockB) {
				this.setBlockB(blockB);
			}

			public void setBlockB(DatedTimeBlock blockB) {
				if (blockB == null) {
					throw new IllegalArgumentException("blockB cannot be null.");
				}

				this.blockB = new DatedTimeBlockImpl(blockB);
			}

			// MARK: DatedTimeBlockSetOperations
			@Override
			public DatedTimeBlock getBlockA() {
				return DatedTimeBlockUtilityInstanceImpl.this.datedTimeBlock;
			}

			@Override
			public DatedTimeBlock getBlockB() {
				return this.blockB;
			}

			@Override
			public boolean hasOverlap() {
				if (this.hasOverlap == null) {
					this.computeOverlap();
				}

				return this.hasOverlap;
			}

			@Override
			public boolean isContinuous() {
				if (this.isContinuous == null) {
					this.computeOverlap();
				}

				return this.isContinuous;
			}

			@Override
			public boolean isInline() {
				if (this.isInline == null) {
					this.isInline = DatedTimeBlockUtilityInstanceImpl.this.isInlineWith(this.blockB);
				}

				return this.isInline;
			}

			public Boolean isLargerThanBlockB() {
				if (this.isLargerThanBlockB == null) {
					Date blockBEndDate = this.getBlockBEndDate();
					this.isLargerThanBlockB = DatedTimeBlockUtilityInstanceImpl.this.getEndDate().after(blockBEndDate);
				}

				return this.isLargerThanBlockB;
			}

			public Date getBlockBEndDate() {
				if (this.blockBEndDate == null) {
					this.blockBEndDate = TimeBlockReaderImpl.this.getDateEnd(this.blockB);
				}

				return this.blockBEndDate;
			}

			private void computeOverlap() {
				Integer comparison = DatedTimeBlockUtilityInstanceImpl.this.compareBlockContinuity(this.blockB);
				this.isContinuous = (comparison <= 0);
				this.hasOverlap = (comparison < 0);
			}

			private void assertHasOverlap() throws TimeBlockNoOverlapException {
				if (!this.hasOverlap()) {
					throw new TimeBlockNoOverlapException();
				}
			}

			private void assertIsContinuous() throws TimeBlockNotContinuousException {
				if (!this.isContinuous()) {
					throw new TimeBlockNotContinuousException();
				}
			}

			@SuppressWarnings("unused")
			public Date getFirstOverlapDate() throws TimeBlockNotContinuousException {
				if (this.firstOverlapDate == null) {
					Date startDate = this.getBlockA().getTimeBlockStart();
					Long timeBlocks = this.getFirstConnectionIndex();
					this.firstOverlapDate = TimeBlockReaderImpl.this.getDateEnd(startDate, timeBlocks);
				}

				return this.firstOverlapDate;
			}

			@Override
			public Long getFirstConnectionIndex() throws TimeBlockNotContinuousException {
				if (this.firstOverlap == null) {
					this.assertIsContinuous();
					this.firstOverlap = this.calculateIndexOfFirstConnection();
				}

				return this.firstOverlap;
			}

			private Long getFirstConnectionBlockLength() throws TimeBlockNotContinuousException {
				return this.getFirstConnectionIndex() + 1L;
			}

			private Long calculateIndexOfFirstConnection() {
				Date startDate = this.getBlockA().getTimeBlockStart();
				Date overlapDate = this.blockB.getTimeBlockStart();
				Long length = TimeBlockReaderImpl.this.getBlocksForDates(startDate, overlapDate,
				        TimeBlockRounding.CEIL);
				return length - 1;
			}

			@Override
			public DatedTimeBlock getUnion() throws TimeBlockNotContinuousException {
				if (this.union == null) {
					this.assertIsContinuous();
					Long totalLength = this.getFirstConnectionBlockLength() + this.blockB.getTimeBlocks();

					if (this.isInline() == false) {
						totalLength -= 1;	// Floor if they're inline with
						                 	// eachother.
					}

					this.union = new DatedTimeBlockImpl(totalLength, this.getBlockA().getTimeBlockStart());
				}

				return this.union;
			}

			@Override
			public DatedTimeBlock getUnique() {
				if (this.unique == null) {
					Long overlapIndex = this.getFirstConnectionBlockLength();

					// If they are not inline (overlap on that index) then don't
					// include that.
					if (this.isInline() == false) {
						overlapIndex -= 1;
					}

					this.unique = new DatedTimeBlockImpl(overlapIndex, this.getBlockA().getTimeBlockStart());
				}

				return this.unique;
			}

			@Override
			public DatedTimeBlock getIntersection() throws TimeBlockNotContinuousException {
				if (this.intersection == null) {
					DatedTimeBlock unique = this.getUnique();
					Date startDate = TimeBlockReaderImpl.this.getDateEnd(unique);
					Long totalLength = null;

					if (this.isLargerThanBlockB()) {
						Date endDate = this.getBlockBEndDate();
						totalLength = TimeBlockReaderImpl.this.getBlocksForDates(startDate, endDate,
						        TimeBlockRounding.FLOOR);
					} else {
						Long uniqueBlocks = unique.getTimeBlocks();
						Long difference = this.getBlockA().getTimeBlocks() - uniqueBlocks;
						totalLength = difference;
					}

					this.intersection = new DatedTimeBlockImpl(totalLength, startDate);
				}

				return this.intersection;
			}

			@Override
			public DatedTimeBlock getCompliment() throws TimeBlockNotContinuousException {
				if (this.compliment == null) {
					DatedTimeBlock intersection = this.getIntersection();
					Long totalLength = this.blockB.getTimeBlocks() - intersection.getTimeBlocks();
					Date startDate = TimeBlockReaderImpl.this.getDateEnd(intersection);
					this.compliment = new DatedTimeBlockImpl(totalLength, startDate);
				}

				return this.compliment;
			}

		}

	}
}
