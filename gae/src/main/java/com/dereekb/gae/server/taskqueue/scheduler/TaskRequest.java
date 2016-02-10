package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.Collection;

import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Represents a request that can be sent by a {@link TaskScheduler}.
 *
 * @author dereekb
 *
 */
public interface TaskRequest {

	/**
	 * Optional request name.
	 *
	 * @return {@link String} request name.
	 */
	public String getName();

	/**
	 * Returns the method type.
	 * <p>
	 * Generally should not be {@code null}.
	 *
	 * @return {@link Method} type.
	 */
	public Method getMethod();

	/**
	 * Returns relative target's path.
	 *
	 * @return {@link SimplePath}. Never {@code null}.
	 */
	public SimplePath getPath();

	/**
	 * Optional collection of request headers.
	 *
	 * @return {@link Collection} of request headers.
	 */
	public Collection<TaskParameter> getHeaders();

	/**
	 * Optional collection of request parameters.
	 *
	 * @return {@link Collection} of request parameters.
	 */
	public Collection<TaskParameter> getParameters();

	/**
	 * Returns timings for when to submit the request.
	 *
	 * @return {@link TaskRequestTiming} or {@code null} if timings are
	 *         unspecified.
	 */
	public TaskRequestTiming getTimings();

}
