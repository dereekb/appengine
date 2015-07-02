package com.dereekb.gae.server.taskqueue.system;

/**
 * Describes the timing for a {@link TaskRequest}.
 *
 * @author dereekb
 *
 */
public interface TaskRequestTiming {

	public TaskRequestTimingType getTimingType();

	public Long getTime();

}
