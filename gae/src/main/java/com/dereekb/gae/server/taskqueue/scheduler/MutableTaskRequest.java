package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.Collection;

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

	public void setTimings(TaskRequestTiming timings);

}
