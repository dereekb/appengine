package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerAuthenticator;
import com.dereekb.gae.server.taskqueue.scheduler.utility.TaskOptionsUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * {@link TaskSchedulerAuthenticator} implementation that uses a
 * {@link SystemLoginTokenFactory} to create token authentication.
 * 
 * @author dereekb
 *
 */
public class TaskSchedulerAuthenticatorImpl
        implements TaskSchedulerAuthenticator {

	private SystemLoginTokenFactory systemLoginTokenFactory;

	public TaskSchedulerAuthenticatorImpl(SystemLoginTokenFactory systemLoginTokenFactory)
	        throws IllegalArgumentException {
		this.setSystemLoginTokenFactory(systemLoginTokenFactory);
	}

	public SystemLoginTokenFactory getSystemLoginTokenFactory() {
		return this.systemLoginTokenFactory;
	}

	public void setSystemLoginTokenFactory(SystemLoginTokenFactory systemLoginTokenFactory)
	        throws IllegalArgumentException {
		if (systemLoginTokenFactory == null) {
			throw new IllegalArgumentException("Token Factory cannot be null.");
		}

		this.systemLoginTokenFactory = systemLoginTokenFactory;
	}

	// MARK: TaskSchedulerAuthenticator
	@Override
	public List<TaskOptions> authenticateOptions(List<TaskOptions> options) {
		KeyedEncodedParameter header = this.systemLoginTokenFactory.makeTokenHeader();
		return TaskOptionsUtility.appendHeader(options, header);

	}

}
