package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterBuilder;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerAuthenticator;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

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

	private AuthenticationParameterBuilder authParameterBuilder = AuthenticationParameterServiceImpl.SINGLETON;

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

	public AuthenticationParameterBuilder getAuthParameterBuilder() {
		return this.authParameterBuilder;
	}

	public void setAuthParameterBuilder(AuthenticationParameterBuilder authParameterBuilder) {
		if (authParameterBuilder == null) {
			throw new IllegalArgumentException("authParameterBuilder cannot be null.");
		}

		this.authParameterBuilder = authParameterBuilder;
	}

	// MARK: TaskSchedulerAuthenticator
	@Override
	public List<TaskOptions> authenticateOptions(List<TaskOptions> options) {

		SignedEncodedLoginToken systemToken = this.systemLoginTokenFactory.makeSystemToken();
		Collection<KeyedEncodedParameter> headers = this.authParameterBuilder
		        .buildAuthenticationParameters(systemToken);

		options = TaskOptionsUtility.appendHeaders(options, headers);

		return options;
	}

	@Override
	public String toString() {
		return "TaskSchedulerAuthenticatorImpl [systemLoginTokenFactory=" + this.systemLoginTokenFactory
		        + ", authParameterBuilder=" + this.authParameterBuilder + "]";
	}

}
