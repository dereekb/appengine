package com.dereekb.gae.utilities.collections.time.utility.impl;

import java.util.Date;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;

/**
 * {@link DatedTimeBlock} implementation.
 * 
 * @author dereekb
 *
 */
public class DatedTimeBlockImpl extends TimeBlockImpl
        implements DatedTimeBlock {

	private Date timeBlockStart;

	public DatedTimeBlockImpl(Long timeBlocks, Date timeBlockStart) {
		super(timeBlocks);
		this.setTimeBlockStart(timeBlockStart);
	}

	@Override
	public Date getTimeBlockStart() {
		return this.timeBlockStart;
	}

	public void setTimeBlockStart(Date timeBlockStart) {
		if (timeBlockStart == null) {
			throw new IllegalArgumentException("timeBlockStart cannot be null.");
		}

		this.timeBlockStart = timeBlockStart;
	}

	@Override
	public String toString() {
		return "DatedTimeBlockImpl [timeBlocks=" + this.getTimeBlocks() + ", timeBlockStart=" + this.timeBlockStart + "]";
	}

}
