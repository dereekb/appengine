package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.List;

import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * Authenticator used by task schedulers to make sure it's requests are properly
 * authenticated.
 * 
 * @author dereekb
 *
 */
public interface TaskSchedulerAuthenticator {

	/**
	 * Adds authentication to the input task options.
	 * 
	 * @param options
	 *            {@link List}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<TaskOptions> authenticateOptions(List<TaskOptions> options);

}
