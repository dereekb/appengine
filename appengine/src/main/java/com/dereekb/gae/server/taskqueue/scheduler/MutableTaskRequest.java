package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestHost;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * {@link TaskRequest} that can be modified.
 *
 * @author dereekb
 *
 */
public interface MutableTaskRequest
        extends TaskRequest {

	public void setName(String name);

	public void setMethod(Method method);

	public void setPath(SimplePath path) throws IllegalArgumentException;

	public void setHeaders(Collection<? extends KeyedEncodedParameter> headers);

	public void setParameters(Collection<? extends KeyedEncodedParameter> parameters);

	/**
	 * Calls {{@link #setRequestData(TaskRequestDataType, String)} with
	 * {@link TaskRequestDataType#JSON} as the default value.
	 *
	 * @param data
	 */
	public void setRequestData(String data);

	/**
	 * Sets the request data. This will override any set parameters.
	 *
	 * @param type
	 *            {@link TaskRequestDataType}. Never {@code null}.
	 * @param data
	 *            {@link String}, or {@code null} to clear any data.
	 */
	public void setRequestData(TaskRequestDataType type,
	                           String data);

	public void setTimings(TaskRequestTiming timings);

	public void setHost(TaskRequestHost host);

}
