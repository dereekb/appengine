package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl.AbstractAdminModelRoleGranterImpl;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link AbstractModelRoleSetLoaderBuilderComponent} for {@link CrudModelRole}
 * values.
 * <p>
 * By default focuses on the owner of an object.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractCrudModelRoleSetLoaderBuilderComponent<T> extends AbstractModelRoleSetLoaderBuilderComponent<T> {

	private SecurityContextModelOwnershipChecker<T> ownershipChecker;

	public AbstractCrudModelRoleSetLoaderBuilderComponent(SecurityContextModelOwnershipChecker<T> ownershipChecker) {
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

	// MARK: AbstractModelRoleSetLoaderBuilderComponent
	@Override
	protected List<ModelRoleGranter<T>> loadGranters() {
		List<ModelRoleGranter<T>> granters = new ArrayList<ModelRoleGranter<T>>();

		ListUtility.addElement(granters, this.makeOwnerGranter());
		ListUtility.addElement(granters, this.makeReadGranter());
		ListUtility.addElement(granters, this.makeUpdateGranter());
		ListUtility.addElement(granters, this.makeDeleteGranter());
		ListUtility.addElement(granters, this.makeSearchGranter());
		ListUtility.addElement(granters, this.makeLinkGranter());

		return granters;
	}

	// MARK: Functions
	protected ModelRoleGranter<T> makeOwnerGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.OWNED);
	}

	protected ModelRoleGranter<T> makeReadGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.READ);
	}

	protected ModelRoleGranter<T> makeUpdateGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.UPDATE);
	}

	protected ModelRoleGranter<T> makeDeleteGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.DELETE);
	}

	protected ModelRoleGranter<T> makeSearchGranter() {
		return new IsOwnerModelRoleGranter(CrudModelRole.SEARCH);
	}

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
			return AbstractCrudModelRoleSetLoaderBuilderComponent.this.ownershipChecker.isOwnedInSecurityContext(model);
		}

	}

}
