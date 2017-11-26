package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.impl.ChildCrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link AbstractCrudModelRoleSetLoaderBuilderComponent} extension for parent
 * models that are granted {@link ChildCrudModelRole} values.
 * <p>
 * By default is granted based on ownership of the object.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractParentChildCrudModelRoleSetLoaderBuilderComponent<T> extends AbstractCrudModelRoleSetLoaderBuilderComponent<T> {

	public AbstractParentChildCrudModelRoleSetLoaderBuilderComponent(
	        SecurityContextModelOwnershipChecker<T> ownershipChecker) {
		super(ownershipChecker);
	}

	// MARK: AbstractModelRoleSetLoaderBuilderComponent
	@Override
	protected List<ModelRoleGranter<T>> loadGranters() {
		List<ModelRoleGranter<T>> granters = super.loadGranters();

		ListUtility.addElement(granters, this.makeChildCreateGranter());
		ListUtility.addElement(granters, this.makeChildReadGranter());
		ListUtility.addElement(granters, this.makeChildUpdateGranter());
		ListUtility.addElement(granters, this.makeChildDeleteGranter());
		ListUtility.addElement(granters, this.makeChildSearchGranter());
		ListUtility.addElement(granters, this.makeChildLinkGranter());

		return granters;
	}

	// MARK: Functions
	protected ModelRoleGranter<T> makeChildCreateGranter() {
		return new IsOwnerModelRoleGranter(ChildCrudModelRole.CHILD_CREATE);
	}

	protected ModelRoleGranter<T> makeChildReadGranter() {
		return new IsOwnerModelRoleGranter(ChildCrudModelRole.CHILD_READ);
	}

	protected ModelRoleGranter<T> makeChildUpdateGranter() {
		return new IsOwnerModelRoleGranter(ChildCrudModelRole.CHILD_UPDATE);
	}

	protected ModelRoleGranter<T> makeChildDeleteGranter() {
		return new IsOwnerModelRoleGranter(ChildCrudModelRole.CHILD_DELETE);
	}

	protected ModelRoleGranter<T> makeChildSearchGranter() {
		return new IsOwnerModelRoleGranter(ChildCrudModelRole.CHILD_SEARCH);
	}

	protected ModelRoleGranter<T> makeChildLinkGranter() {
		return new IsOwnerModelRoleGranter(ChildCrudModelRole.CHILD_LINK);
	}

}
