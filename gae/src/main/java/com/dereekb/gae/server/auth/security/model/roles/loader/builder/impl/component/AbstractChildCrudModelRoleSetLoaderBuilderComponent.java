package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component;

import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
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

	private ParentModelRoleSetContextReader<T> parentContextReader;

	public ParentModelRoleSetContextReader<T> getParentContextReader() {
		return this.parentContextReader;
	}

	public void setParentContextReader(ParentModelRoleSetContextReader<T> parentContextReader) {
		if (parentContextReader == null) {
			throw new IllegalArgumentException("parentContextReader cannot be null.");
		}

		this.parentContextReader = parentContextReader;
	}




}
