package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerAuthenticator;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * {@link TaskSchedulerAuthenticator} implementation.
 * 
 * @author dereekb
 *
 */
public class TaskSchedulerAuthenticatorImpl
        implements TaskSchedulerAuthenticator {

	// MARK: TaskSchedulerAuthenticator
	@Override
	public List<TaskOptions> authenticateOptions(List<TaskOptions> options) {
		// TODO Auto-generated method stub
		return null;
	}

}
