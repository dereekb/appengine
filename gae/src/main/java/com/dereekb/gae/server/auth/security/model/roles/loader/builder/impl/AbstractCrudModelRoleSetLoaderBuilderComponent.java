package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
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
	 *
	 * @author dereekb
	 *
	 */
	protected class IsOwnerModelRoleGranter extends AbstractModelRoleGranterImpl {

		public IsOwnerModelRoleGranter(ModelRole grantedRole) {
			super(grantedRole);
		}

		@Override
		public boolean hasRole(T model) {
			return currentContextIsOwnerOfModel(model);
		}

	}

	// MARK: Internal
	/**
	 * Whether or not the model is owned.
	 *
	 * @param model
	 * @return {@code true} if the current login owns the model.
	 */
	protected abstract boolean currentContextIsOwnerOfModel(T model);

}
