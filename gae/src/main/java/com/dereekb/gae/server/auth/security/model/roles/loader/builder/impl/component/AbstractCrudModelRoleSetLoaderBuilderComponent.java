package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link AbstractModelRoleSetLoaderBuilderComponent} for {@link CrudModelRole}
 * values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @see AbstractOwnershipCrudModelRoleSetLoaderBuilderComponent
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
	protected abstract ModelRoleGranter<T> makeOwnerGranter();

	protected abstract ModelRoleGranter<T> makeReadGranter();

	protected abstract ModelRoleGranter<T> makeUpdateGranter();

	protected abstract ModelRoleGranter<T> makeDeleteGranter();

	protected abstract ModelRoleGranter<T> makeSearchGranter();

	protected abstract ModelRoleGranter<T> makeLinkGranter();

}
