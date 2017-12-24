package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl.AbstractModelRoleGranterImpl;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract builder for {@link ModelRolesGranter} that loads another model to
 * use for granting decisions.
 *
 * @author dereekb
 *
 * @param <T>
 *            loaded model type
 */
public abstract class AbstractRelatedModelRolesGranterBuilder<T extends UniqueModel> {

	protected Getter<T> getter;

	public AbstractRelatedModelRolesGranterBuilder(Getter<T> getter) {
		this.setGetter(getter);
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	protected abstract class AbstractRelatedGranter<K> extends AbstractModelRoleGranterImpl<K> {

		public AbstractRelatedGranter(ModelRole grantedRole) {
			super(grantedRole);
		}

		// MARK: AbstractModelRoleGranterImpl
		@Override
		public boolean hasRole(K model) {

			ModelKey relatedKey = this.getRelatedKey(model);
			T related = AbstractRelatedModelRolesGranterBuilder.this.getter.get(relatedKey);

			if (related != null) {
				return this.shouldGrantRole(related, model);
			} else {
				return this.shouldGrantRoleForNullRelated();
			}
		}

		protected abstract ModelKey getRelatedKey(K model);

		protected abstract boolean shouldGrantRole(T related, K model);

		protected boolean shouldGrantRoleForNullRelated() {
			return false;
		}

	}

}
