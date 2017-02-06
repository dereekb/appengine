package com.dereekb.gae.server.taskqueue.scheduler;

/**
 * Describes the timing for a {@link TaskRequest}.
 *
 * @author dereekb
 *
 */
public interface TaskRequestTiming {

	/**
	 * @return {@link TaskRequestTimingType}. Never {@code null}.
	 */
	public TaskRequestTimingType getTimingType();

	/**
	 * Returns the request time in milliseconds.
	 * <p>
	 * Must be greater than or equal to 0.
	 *
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getTime();

}
