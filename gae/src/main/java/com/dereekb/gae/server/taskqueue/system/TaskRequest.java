package com.dereekb.gae.server.taskqueue.system;

import java.util.Collection;

import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Represents a request that can be sent by a {@link TaskRequestSystem}.
 *
 * @author dereekb
 *
 */
public interface TaskRequest {

	/**
	 *
	 * @return unique request name.
	 */
	public String getName();

	/**
	 *
	 * @return request method type.
	 */
	public Method getMethod();

	/**
	 *
	 * @return relative request url.
	 */
	public String getUrl();

	/**
	 *
	 * @return headers for the request.
	 */
	public Collection<TaskParameter> getHeaders();

	/**
	 * @return parameters for the request.
	 */
	public Collection<TaskParameter> getParameters();

	/**
	 * @return Timings for when to submit the request. Null if it should be
	 *         submitted immediately.
	 */
	public TaskRequestTiming getTimings();

}
