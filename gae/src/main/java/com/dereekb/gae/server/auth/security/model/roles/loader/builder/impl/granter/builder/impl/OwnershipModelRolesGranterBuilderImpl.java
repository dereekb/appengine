package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder.impl;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder.OwnershipModelRolesGranterBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl.AdminModelRoleGranterImpl;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;

/**
 * {@link OwnershipModelRolesGranterBuilder} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class OwnershipModelRolesGranterBuilderImpl<T>
        implements OwnershipModelRolesGranterBuilder<T> {

	private SecurityContextModelOwnershipChecker<T> ownershipChecker;

	public OwnershipModelRolesGranterBuilderImpl(SecurityContextModelOwnershipChecker<T> ownershipChecker) {
		super();
		this.setOwnershipChecker(ownershipChecker);
	}

	public SecurityContextModelOwnershipChecker<T> getOwnershipChecker() {
		return this.ownershipChecker;
	}

	public void setOwnershipChecker(SecurityContextModelOwnershipChecker<T> ownershipChecker) {
		if (ownershipChecker == null) {
			throw new IllegalArgumentException("ownershipChecker cannot be null.");
		}

		this.ownershipChecker = ownershipChecker;
	}

	// MARK: Builder
	@Override
	public ModelRoleGranter<T> makeGranterForRole(ModelRole grantedRole) {
		return new IsOwnerModelRoleGranter(grantedRole);
	}

	// MARK: Granters
	/**
	 * {@link ModelRoleGranter} that grants the role if the model is owned by
	 * the current security context.
	 *
	 * @author dereekb
	 *
	 */
	protected class IsOwnerModelRoleGranter extends AdminModelRoleGranterImpl<T> {

		public IsOwnerModelRoleGranter(ModelRole grantedRole) {
			super(grantedRole);
		}

		@Override
		public boolean nonAdminHasRole(T model) {
			return OwnershipModelRolesGranterBuilderImpl.this.ownershipChecker.isOwnedInSecurityContext(model);
		}

	}

	@Override
	public String toString() {
		return "OwnershipModelRolesGranterBuilderImpl [ownershipChecker=" + this.ownershipChecker + "]";
	}

}
