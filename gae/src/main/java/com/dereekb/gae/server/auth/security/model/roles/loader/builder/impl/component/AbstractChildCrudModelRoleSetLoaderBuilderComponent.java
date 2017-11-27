package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component;

import com.dereekb.gae.server.auth.security.model.roles.impl.ChildCrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder.impl.ParentChildModelRoleGranterBuilderImpl;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;

/**
 * {@link AbstractOwnershipCrudModelRoleSetLoaderBuilderComponent} extension for
 * child models that receive their {@link CrudModelRole} values from a parent.
 * <p>
 * By default is granted based on ownership of the object.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractChildCrudModelRoleSetLoaderBuilderComponent<T> extends AbstractCrudModelRoleSetLoaderBuilderComponent<T> {

	private ParentChildModelRoleGranterBuilderImpl<T> parentRolesGranter;

	public AbstractChildCrudModelRoleSetLoaderBuilderComponent(ParentModelRoleSetContextReader<T> parentContextReader) {
		super();
		this.setParentContextReader(parentContextReader);
	}

	public void setParentContextReader(ParentModelRoleSetContextReader<T> parentContextReader) {
		this.parentRolesGranter = new ParentChildModelRoleGranterBuilderImpl<T>();
	}

	// MARK: Functions
	@Override
	protected ModelRoleGranter<T> makeOwnerGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(CrudModelRole.OWNED, CrudModelRole.OWNED);
	}

	@Override
	protected ModelRoleGranter<T> makeReadGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(CrudModelRole.READ, ChildCrudModelRole.CHILD_READ);
	}

	@Override
	protected ModelRoleGranter<T> makeUpdateGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(CrudModelRole.UPDATE, ChildCrudModelRole.CHILD_UPDATE);
	}

	@Override
	protected ModelRoleGranter<T> makeDeleteGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(CrudModelRole.DELETE, ChildCrudModelRole.CHILD_DELETE);
	}

	@Override
	protected ModelRoleGranter<T> makeSearchGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(CrudModelRole.SEARCH, ChildCrudModelRole.CHILD_SEARCH);
	}

	@Override
	protected ModelRoleGranter<T> makeLinkGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(CrudModelRole.LINK, ChildCrudModelRole.CHILD_LINK);
	}

}
