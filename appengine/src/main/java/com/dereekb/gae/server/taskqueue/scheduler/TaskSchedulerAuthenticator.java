package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.List;

/**
 * Authenticator used by task schedulers to make sure it's requests are properly
 * authenticated.
 *
 * @author dereekb
 *
 */
public interface TaskSchedulerAuthenticator {

	/**
	 * Converts the {@link TaskRequest} to {@link SecuredTaskRequest} values.
	 *
	 * @param options
	 *            {@link List}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<SecuredTaskRequest> authenticateOptions(List<TaskRequest> requests);

}
