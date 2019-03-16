package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.impl.ChildCrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link AbstractChildCrudModelRoleSetLoaderBuilderComponent} extension that
 * also transitively grants {@link ChildCrudModelRole} values if the parent has
 * them.
 * <p>
 * Use this for cases where the "child" is also considered a parent.
 * <p>
 * Don't confuse this with
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent}, which is
 * used for granting child roles.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractChildAndParentCrudModelRoleSetLoaderBuilderComponent<T> extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<T> {

	public AbstractChildAndParentCrudModelRoleSetLoaderBuilderComponent(
	        ParentModelRoleSetContextReader<T> parentContextReader) {
		super(parentContextReader);
	}

	// MARK: AbstractModelRoleSetLoaderBuilderComponent
	@Override
	protected List<ModelRoleGranter<T>> loadGranters() {
		List<ModelRoleGranter<T>> granters = super.loadGranters();

		ListUtility.addElement(granters, this.makeTransitiveChildCreateGranter());
		ListUtility.addElement(granters, this.makeTransitiveChildReadGranter());
		ListUtility.addElement(granters, this.makeTransitiveChildUpdateGranter());
		ListUtility.addElement(granters, this.makeTransitiveChildDeleteGranter());
		ListUtility.addElement(granters, this.makeTransitiveChildSearchGranter());
		ListUtility.addElement(granters, this.makeTransitiveChildLinkGranter());

		return granters;
	}

	// MARK: Functions
	protected ModelRoleGranter<T> makeTransitiveChildCreateGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(ChildCrudModelRole.CHILD_CREATE);
	}

	protected ModelRoleGranter<T> makeTransitiveChildReadGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(ChildCrudModelRole.CHILD_READ);
	}

	protected ModelRoleGranter<T> makeTransitiveChildUpdateGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(ChildCrudModelRole.CHILD_UPDATE);
	}

	protected ModelRoleGranter<T> makeTransitiveChildDeleteGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(ChildCrudModelRole.CHILD_DELETE);
	}

	protected ModelRoleGranter<T> makeTransitiveChildSearchGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(ChildCrudModelRole.CHILD_SEARCH);
	}

	protected ModelRoleGranter<T> makeTransitiveChildLinkGranter() {
		return this.parentRolesGranter.makeGrantRoleForParentRole(ChildCrudModelRole.CHILD_LINK);
	}

}
