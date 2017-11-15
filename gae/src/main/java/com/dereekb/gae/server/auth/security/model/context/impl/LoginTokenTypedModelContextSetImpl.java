package com.dereekb.gae.server.auth.security.model.context.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;

/**
 * {@link LoginTokenTypedModelContextSet} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenTypedModelContextSetImpl
        implements LoginTokenTypedModelContextSet {

	private String modelType;
	private List<LoginTokenModelContext> contexts;

	public LoginTokenTypedModelContextSetImpl(String modelType, List<LoginTokenModelContext> contexts) {
		super();
		this.setModelType(modelType);
		this.setContexts(contexts);
	}

	// MARK: LoginTokenTypedModelContextSet
	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	@Override
	public List<LoginTokenModelContext> getContexts() {
		return this.contexts;
	}

	public void setContexts(List<LoginTokenModelContext> contexts) {
		if (contexts == null) {
			throw new IllegalArgumentException("contexts cannot be null.");
		}

		this.contexts = contexts;
	}

	@Override
	public String toString() {
		return "LoginTokenTypedModelContextSetImpl [modelType=" + this.modelType + ", contexts=" + this.contexts + "]";
	}

}
