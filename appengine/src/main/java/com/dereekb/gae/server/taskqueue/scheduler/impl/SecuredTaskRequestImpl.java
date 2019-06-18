package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.SecuredTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * {@link SecuredTaskRequest} implementation.
 *
 * @author dereekb
 *
 */
public class SecuredTaskRequestImpl implements SecuredTaskRequest {

	private TaskRequest taskRequest;
	private Collection<KeyedEncodedParameter> securityHeaders;

	public SecuredTaskRequestImpl(TaskRequest taskRequest, Collection<KeyedEncodedParameter> securityHeaders) {
		super();
		this.setTaskRequest(taskRequest);
		this.setSecurityHeaders(securityHeaders);
	}

	@Override
	public TaskRequest getTaskRequest() {
		return this.taskRequest;
	}

	public void setTaskRequest(TaskRequest taskRequest) {
		if (taskRequest == null) {
			throw new IllegalArgumentException("taskRequest cannot be null.");
		}

		this.taskRequest = taskRequest;
	}

	@Override
	public Collection<KeyedEncodedParameter> getSecurityHeaders() {
		return this.securityHeaders;
	}

	public void setSecurityHeaders(Collection<KeyedEncodedParameter> securityHeaders) {
		if (securityHeaders == null) {
			throw new IllegalArgumentException("securityHeaders cannot be null.");
		}

		this.securityHeaders = securityHeaders;
	}

	@Override
	public String toString() {
		return "SecuredTaskRequestImpl [taskRequest=" + this.taskRequest + ", securityHeaders=" + this.securityHeaders
		        + "]";
	}

}
