package com.dereekb.gae.server.auth.security.model.context.impl;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.datastore.models.impl.AbstractUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextImpl extends AbstractUniqueModel
        implements LoginTokenModelContext {

	private String modelType;
	private ModelKey modelKey;
	private ModelRoleSet roleSet;

	public LoginTokenModelContextImpl() {}

	public LoginTokenModelContextImpl(String modelType, ModelKey modelKey, ModelRoleSet roleSet) {
		super();
		this.setModelType(modelType);
		this.setModelKey(modelKey);
		this.setRoleSet(roleSet);
	}

	// MARK: LoginTokenModelContext
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
	public ModelKey getModelKey() {
		return this.modelKey;
	}

	public void setModelKey(ModelKey modelKey) {
		if (modelKey == null) {
			throw new IllegalArgumentException("modelKey cannot be null.");
		}

		this.modelKey = modelKey;
	}

	@Override
	public ModelRoleSet getRoleSet() {
		return this.roleSet;
	}

	public void setRoleSet(ModelRoleSet roleSet) {
		if (roleSet == null) {
			throw new IllegalArgumentException("roleSet cannot be null.");
		}

		this.roleSet = roleSet;
	}

	@Override
	public String toString() {
		return "LoginTokenModelContextImpl [modelType=" + this.modelType + ", modelKey=" + this.modelKey + ", roleSet="
		        + this.roleSet + "]";
	}

}
