package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.Collection;

import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * {@link TaskRequest} wrapper that also contains security headers.
 *
 * @author dereekb
 *
 */
public interface SecuredTaskRequest {

	/**
	 * Returns the task request.
	 *
	 * @return {@link TaskRequest}. Never {@code null}.
	 */
	public TaskRequest getTaskRequest();

	/**
	 * Optional collection of request security headers.
	 *
	 * @return {@link Collection} of request security headers.
	 */
	public Collection<KeyedEncodedParameter> getSecurityHeaders();

}
