package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl.AbstractAdminModelRoleGranterImpl;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;

/**
 * {@link AbstractCrudModelRoleSetLoaderBuilderComponent} that assigns roles by
 * default based on ownership.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractOwnershipCrudModelRoleSetLoaderBuilderComponent<T> extends AbstractCrudModelRoleSetLoaderBuilderComponent<T> {

	private SecurityContextModelOwnershipChecker<T> ownershipChecker;

	public AbstractOwnershipCrudModelRoleSetLoaderBuilderComponent(
	        SecurityContextModelOwnershipChecker<T> ownershipChecker) {
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

	// MARK: Functions
	@Override
	protected ModelRoleGranter<T> makeOwnerGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.OWNED);
	}

	@Override
	protected ModelRoleGranter<T> makeReadGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.READ);
	}

	@Override
	protected ModelRoleGranter<T> makeUpdateGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.UPDATE);
	}

	@Override
	protected ModelRoleGranter<T> makeDeleteGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.DELETE);
	}

	@Override
	protected ModelRoleGranter<T> makeSearchGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.SEARCH);
	}

	@Override
	protected ModelRoleGranter<T> makeLinkGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.LINK);
	}

	// MARK: Granters
	/**
	 * {@link ModelRoleGranter} that grants the role if the model is owned by
	 * the current security context.
	 *
	 * @author dereekb
	 *
	 */
	protected class IsOwnerModelRoleGranter extends AbstractAdminModelRoleGranterImpl<T> {

		public IsOwnerModelRoleGranter(ModelRole grantedRole) {
			super(grantedRole);
		}

		@Override
		public boolean nonAdminHasRole(T model) {
			return AbstractOwnershipCrudModelRoleSetLoaderBuilderComponent.this.ownershipChecker
			        .isOwnedInSecurityContext(model);
		}

	}

}
