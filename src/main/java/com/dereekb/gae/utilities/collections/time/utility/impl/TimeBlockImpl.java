package com.dereekb.gae.utilities.collections.time.utility.impl;

import com.dereekb.gae.utilities.collections.time.TimeBlock;

/**
 * {@link TimeBlock} implementation.
 * 
 * @author dereekb
 *
 */
public class TimeBlockImpl
        implements TimeBlock {

	private Long timeBlocks;

	public TimeBlockImpl() {
		this(0L);
	};
	
	public TimeBlockImpl(Long timeBlocks) {
		this.setTimeBlocks(timeBlocks);
	}

	@Override
	public Long getTimeBlocks() {
		return this.timeBlocks;
	}

	public void setTimeBlocks(Long timeBlocks) {
		if (timeBlocks == null) {
			throw new IllegalArgumentException("timeBlocks cannot be null.");
		}

		this.timeBlocks = timeBlocks;
	}

	@Override
	public String toString() {
		return "TimeBlockImpl [timeBlocks=" + this.timeBlocks + "]";
	}

}
