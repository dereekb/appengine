package com.dereekb.gae.server.taskqueue.scheduler;

import java.net.URI;
import java.util.Collection;

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
	 * Returns relative target URI.
	 *
	 * @return {@link URI}. Never {@code null}.
	 */
	public URI getUri();

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
