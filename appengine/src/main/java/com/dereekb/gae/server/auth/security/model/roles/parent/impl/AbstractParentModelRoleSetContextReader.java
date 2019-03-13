package com.dereekb.gae.server.auth.security.model.roles.parent.impl;

import com.dereekb.gae.model.exception.NoParentException;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextGetter;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ParentModelRoleSetContextReader} implementation.
 *
 * @author dereekb
 *
 * @param <P>
 *            parent model type
 * @param <T>
 *            model type
 */
public abstract class AbstractParentModelRoleSetContextReader<T extends UniqueModel, P extends UniqueModel>
        implements ParentModelRoleSetContextReader<T> {

	private ModelRoleSetContextGetter<P> parentGetter;

	public AbstractParentModelRoleSetContextReader(ModelRoleSetContextGetter<P> parentGetter) {
		this.setParentGetter(parentGetter);
	}

	public ModelRoleSetContextGetter<P> getParentGetter() {
		return this.parentGetter;
	}

	public void setParentGetter(ModelRoleSetContextGetter<P> parentGetter) {
		if (parentGetter == null) {
			throw new IllegalArgumentException("parentGetter cannot be null.");
		}

		this.parentGetter = parentGetter;
	}

	// MARK: ParentModelRoleSetContextReader
	@Override
	public AnonymousModelRoleSetContext getParentRoleSetContext(T child) throws NoParentException {
		ModelKey parentKey = this.getParentModelKey(child);

		if (parentKey == null) {
			throw new NoParentException(child.getModelKey());
		}

		return this.parentGetter.getAnonymous(parentKey);
	}

	protected abstract ModelKey getParentModelKey(T child);

}
