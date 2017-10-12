package com.dereekb.gae.utilities.collections.time.utility.impl;

import java.util.Date;

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
		public DatedTimeBlockSetOperations operatorWith(DatedTimeBlock datedTimeBlock) {
			return new DatedTimeBlockSetOperationsImpl(datedTimeBlock);
		}

		private class DatedTimeBlockSetOperationsImpl
		        implements DatedTimeBlockSetOperations {

			private DatedTimeBlock blockB;
			private transient Boolean isContinuous;
			private transient Boolean hasOverlap;
			private transient Long firstOverlap;
			private transient Date firstOverlapDate;
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

				this.blockB = blockB;
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
			
			private void computeOverlap() {
				Integer comparison = this.blockB.getTimeBlockStart()
				        .compareTo(DatedTimeBlockUtilityInstanceImpl.this.getEndDate());
				this.isContinuous = (comparison <= 0);
				this.hasOverlap = (comparison < 0);
			}

			private void assertIsContinuous() throws TimeBlockNoOverlapException {
				if (!this.isContinuous()) {
					throw new TimeBlockNoOverlapException();
				}
			}

			public Date getFirstOverlapDate() throws TimeBlockNoOverlapException {
				if (this.firstOverlapDate == null) {
					Date startDate = this.getBlockA().getTimeBlockStart();
					Long timeBlocks = this.getFirstOverlapIndex();
					this.firstOverlapDate = TimeBlockReaderImpl.this.getDateEnd(startDate, timeBlocks);
				}

				return this.firstOverlapDate;
			}

			@Override
			public Long getFirstOverlapIndex() throws TimeBlockNoOverlapException {
				if (this.firstOverlap == null) {
					this.assertIsContinuous();
					this.firstOverlap = this.calculateFirstOverlapIndex();
				}

				return this.firstOverlap;
			}
			
			private Long getFirstOverlapBlockLength() throws TimeBlockNoOverlapException {
				return this.getFirstOverlapIndex() + 1L;
			}

			private Long calculateFirstOverlapIndex() {
				Date startDate = this.getBlockA().getTimeBlockStart();
				Date overlapDate = this.blockB.getTimeBlockStart();
				Long length = TimeBlockReaderImpl.this.getBlocksForDates(startDate, overlapDate,
				        TimeBlockRounding.CEIL);
				return length - 1;
			}

			@Override
			public DatedTimeBlock getUnion() throws TimeBlockNoOverlapException {
				if (this.union == null) {
					this.assertIsContinuous();
					Long totalLength = this.getFirstOverlapBlockLength() + this.blockB.getTimeBlocks();
					this.union = new DatedTimeBlockImpl(totalLength, this.getBlockA().getTimeBlockStart());
				}

				return this.union;
			}

			@Override
			public DatedTimeBlock getUnique() {
				if (this.unique == null) {
					Long overlapIndex = this.getFirstOverlapBlockLength();
					
					// If they overlap (not continuous) then don't include that.
					if (this.hasOverlap()) {
						overlapIndex -= 1;
					}
					
					this.unique = new DatedTimeBlockImpl(overlapIndex, this.getBlockA().getTimeBlockStart());
				}

				return this.unique;
			}

			@Override
			public DatedTimeBlock getIntersection() throws TimeBlockNoOverlapException {
				if (this.intersection == null) {
					DatedTimeBlock unique = this.getUnique();
					Long uniqueBlocks = unique.getTimeBlocks();
					Long difference = this.getBlockA().getTimeBlocks() - uniqueBlocks;
					Date startDate = TimeBlockReaderImpl.this.getDateEnd(unique);
					this.intersection = new DatedTimeBlockImpl(difference, startDate);
				}

				return this.intersection;
			}

			@Override
			public DatedTimeBlock getCompliment() throws TimeBlockNoOverlapException {
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
