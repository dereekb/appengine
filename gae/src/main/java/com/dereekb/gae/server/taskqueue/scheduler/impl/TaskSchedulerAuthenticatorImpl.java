package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterBuilder;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerAuthenticator;
import com.dereekb.gae.server.taskqueue.scheduler.utility.TaskOptionsUtility;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
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

	private AuthenticationParameterBuilder authParameterBuilder = AuthenticationParameterServiceImpl.SINGLETON;
	private String signatureHeader = AppLoginSecurityVerifierService.DEFAULT_SIGNATURE_HEADER;

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

	public String getSignatureHeader() {
		return this.signatureHeader;
	}

	public void setSignatureHeader(String signatureHeader) {
		if (signatureHeader == null) {
			throw new IllegalArgumentException("signatureHeader cannot be null.");
		}

		this.signatureHeader = signatureHeader;
	}

	// MARK: TaskSchedulerAuthenticator
	@Override
	public List<TaskOptions> authenticateOptions(List<TaskOptions> options) {

		SignedEncodedLoginToken systemToken = this.systemLoginTokenFactory.makeSystemToken();

		String token = systemToken.getEncodedLoginToken();
		String signature = systemToken.getTokenSignature();

		KeyedEncodedParameter tokenParam = this.authParameterBuilder.buildAuthenticationParameter(token);
		KeyedEncodedParameter sigParam = new KeyedEncodedParameterImpl(this.signatureHeader, signature);

		Collection<KeyedEncodedParameter> headers = ListUtility.toList(tokenParam, sigParam);
		options = TaskOptionsUtility.appendHeaders(options, headers);

		return options;
	}

	@Override
	public String toString() {
		return "TaskSchedulerAuthenticatorImpl [systemLoginTokenFactory=" + this.systemLoginTokenFactory
		        + ", authParameterBuilder=" + this.authParameterBuilder + ", signatureHeader=" + this.signatureHeader
		        + "]";
	}

}
