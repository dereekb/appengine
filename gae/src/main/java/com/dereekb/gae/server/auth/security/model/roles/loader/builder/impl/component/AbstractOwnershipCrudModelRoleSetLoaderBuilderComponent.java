package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component;

import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder.OwnershipModelRolesGranterBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder.impl.OwnershipModelRolesGranterBuilderImpl;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;

/**
 * {@link AbstractCrudModelRoleSetLoaderBuilderComponent} that uses a
 * {@link OwnershipModelRolesGranterBuilder} to assign {@link CrudModelRole}
 * values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractOwnershipCrudModelRoleSetLoaderBuilderComponent<T> extends AbstractCrudModelRoleSetLoaderBuilderComponent<T> {

	protected OwnershipModelRolesGranterBuilderImpl<T> ownershipRolesGranter;

	public AbstractOwnershipCrudModelRoleSetLoaderBuilderComponent(
	        SecurityContextModelOwnershipChecker<T> ownershipChecker) {
		super();
		this.setOwnershipChecker(ownershipChecker);
	}

	public void setOwnershipChecker(SecurityContextModelOwnershipChecker<T> ownershipChecker) {
		this.ownershipRolesGranter = new OwnershipModelRolesGranterBuilderImpl<T>(ownershipChecker);
	}

	// MARK: Functions
	@Override
	protected ModelRoleGranter<T> makeOwnerGranter() {
		return this.ownershipRolesGranter.makeGranterForRole(CrudModelRole.OWNED);
	}

	@Override
	protected ModelRoleGranter<T> makeReadGranter() {
		return this.ownershipRolesGranter.makeGranterForRole(CrudModelRole.READ);
	}

	@Override
	protected ModelRoleGranter<T> makeUpdateGranter() {
		return this.ownershipRolesGranter.makeGranterForRole(CrudModelRole.UPDATE);
	}

	@Override
	protected ModelRoleGranter<T> makeDeleteGranter() {
		return this.ownershipRolesGranter.makeGranterForRole(CrudModelRole.DELETE);
	}

	@Override
	protected ModelRoleGranter<T> makeSearchGranter() {
		return this.ownershipRolesGranter.makeGranterForRole(CrudModelRole.SEARCH);
	}

	@Override
	protected ModelRoleGranter<T> makeLinkGranter() {
		return this.ownershipRolesGranter.makeGranterForRole(CrudModelRole.LINK);
	}

}
