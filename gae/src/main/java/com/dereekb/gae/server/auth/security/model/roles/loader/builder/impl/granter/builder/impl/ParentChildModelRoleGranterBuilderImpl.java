package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder.impl;

import com.dereekb.gae.model.exception.NoParentException;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder.ParentChildModelRoleGranterBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl.AbstractAdminModelRoleGranterImpl;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;

/**
 * {@link ParentChildModelRoleGranterBuilder} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ParentChildModelRoleGranterBuilderImpl<T>
        implements ParentChildModelRoleGranterBuilder<T> {

	private ParentModelRoleSetContextReader<T> parentContextReader;

	public ParentChildModelRoleGranterBuilderImpl(ParentModelRoleSetContextReader<T> parentContextReader) {
		this.setParentContextReader(parentContextReader);
	}

	public ParentModelRoleSetContextReader<T> getParentContextReader() {
		return this.parentContextReader;
	}

	public void setParentContextReader(ParentModelRoleSetContextReader<T> parentContextReader) {
		if (parentContextReader == null) {
			throw new IllegalArgumentException("parentContextReader cannot be null.");
		}

		this.parentContextReader = parentContextReader;
	}

	// MARK: Builder
	@Override
	public ModelRoleGranter<T> makeGrantRoleForParentRole(ModelRole role) {
		return new ParentChildModelRoleGranter(role);
	}

	@Override
	public ModelRoleGranter<T> makeGrantRoleForParentRole(ModelRole grantedRole,
	                                                      ModelRole parentRole) {
		return new ParentChildModelRoleGranter(grantedRole, parentRole);
	}

	// MARK: Granters
	/**
	 * {@link ModelRoleGranter} that grants the role if the parent context has a
	 * specific role.
	 *
	 * @author dereekb
	 *
	 */
	protected class ParentChildModelRoleGranter extends AbstractAdminModelRoleGranterImpl<T> {

		private final ModelRole parentRole;

		public ParentChildModelRoleGranter(ModelRole role) {
			this(role, role);
		}

		public ParentChildModelRoleGranter(ModelRole grantedRole, ModelRole parentRole) {
			super(grantedRole);
			this.parentRole = parentRole;
		}

		// MARK: AbstractAdminModelRoleGranterImpl
		@Override
		public boolean nonAdminHasRole(T model) {
			try {
				AnonymousModelRoleSetContext context = ParentChildModelRoleGranterBuilderImpl.this.parentContextReader
				        .getParentRoleSetContext(model);
				ModelRoleSet roleSet = context.getRoleSet();
				return roleSet.hasRole(this.parentRole);
			} catch (NoParentException e) {
				return false;
			}
		}

	}

	@Override
	public String toString() {
		return "ParentChildModelRoleGranterBuilderImpl [parentContextReader=" + this.parentContextReader + "]";
	}

}
