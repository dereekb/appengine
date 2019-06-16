package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestHost;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.path.SimplePath;

/**
 * Represents a request that can be sent by a {@link TaskScheduler}.
 *
 * @author dereekb
 *
 */
public interface TaskRequest {

	/**
	 * @return {@link TaskRequestDataType}. Never {@code null}.
	 */
	public TaskRequestDataType getDataType();

	/**
	 * Optional request name.
	 *
	 * @return {@link String} request name.
	 */
	public String getName();

	/**
	 * Returns relative target's path.
	 *
	 * @return {@link SimplePath}. Never {@code null} or empty.
	 */
	public SimplePath getPath();

	/**
	 * Optional collection of request headers.
	 *
	 * @return {@link Collection} of request headers.
	 */
	public Collection<KeyedEncodedParameter> getHeaders();

	/**
	 * Optional collection of request parameters.
	 *
	 * @return {@link Collection} of request parameters.
	 */
	public Collection<KeyedEncodedParameter> getParameters();

	/**
	 * Returns the data for this request, if set.
	 *
	 * @return {@link String} data, or {@code null} if not set.
	 */
	public String getRequestData();

	/**
	 * Returns timings for when to submit the request.
	 *
	 * @return {@link TaskRequestTiming} or {@code null} if timings are
	 *         unspecified.
	 */
	public TaskRequestTiming getTimings();

	/**
	 * Returns the host to send this request to.
	 *
	 * @return {@link TaskRequestHost}, or {@code null} if no host specified.
	 */
	public TaskRequestHost getHost();

}
