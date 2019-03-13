package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideDelegate} implementation.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractSecurityModelRolesQueryTaskOverrideDelegate<D extends LoginTokenUserDetails<?>, Q>
        implements AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> {

	private AnonymousModelRoleSetContextService roleService;

	public AbstractSecurityModelRolesQueryTaskOverrideDelegate(AnonymousModelRoleSetContextService roleService) {
		this.setRoleService(roleService);
	}

	public AnonymousModelRoleSetContextService getRoleService() {
		return this.roleService;
	}

	public void setRoleService(AnonymousModelRoleSetContextService roleService) {
		if (roleService == null) {
			throw new IllegalArgumentException("roleService cannot be null.");
		}

		this.roleService = roleService;
	}

	// MARK: AbstractSecurityModelQueryTaskOverrideDelegate
	@Override
	public void updateQueryForUser(Q query,
	                               D details)
	        throws InvalidAttributeException,
	            NoModelKeyException {
		this.updateQueryForUser(query, details, this.roleService);
	}

	protected abstract void updateQueryForUser(Q query,
	                                           D details,
	                                           AnonymousModelRoleSetContextService roleService)
	        throws InvalidAttributeException,
	            NoModelKeyException;

	@Override
	public String toString() {
		return "AbstractSecurityModelRolesQueryTaskOverrideDelegate [roleService=" + this.roleService + "]";
	}

}
