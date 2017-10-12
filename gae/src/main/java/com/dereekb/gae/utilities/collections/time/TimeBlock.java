package com.dereekb.gae.utilities.collections.time;

/**
 * Immutable block of time.
 * 
 * @author dereekb
 *
 */
public interface TimeBlock {

	/**
	 * Returns the number of periods within this time block.
	 * 
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getTimeBlocks();
	
}
