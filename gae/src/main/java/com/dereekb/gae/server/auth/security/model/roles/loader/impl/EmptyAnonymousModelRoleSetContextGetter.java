package com.dereekb.gae.server.auth.security.model.roles.loader.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.impl.EmptyModelRoleSetImpl;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextGetter;
import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.impl.UniqueModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Empty {@link AnonymousModelRoleSetContextGetter} implementation.
 *
 * @author dereekb
 *
 */
public class EmptyAnonymousModelRoleSetContextGetter extends TypedModelImpl
        implements AnonymousModelRoleSetContextGetter {

	public EmptyAnonymousModelRoleSetContextGetter(String modelType) {
		super(modelType);
	}

	// MARK: AnonymousModelRoleSetContextGetter
	@Override
	public AnonymousModelRoleSetContext get(ModelKey key) throws IllegalArgumentException {
		return new EmptyAnonymousModelRoleSetContext(key);
	}

	@Override
	public List<? extends AnonymousModelRoleSetContext> getWithKeys(Iterable<ModelKey> keys) {
		return Collections.emptyList();
	}

	private class EmptyAnonymousModelRoleSetContext extends UniqueModelImpl
	        implements AnonymousModelRoleSetContext {

		public EmptyAnonymousModelRoleSetContext(ModelKey modelKey) {
			super(modelKey);
		}

		// MARK: AnonymousModelRoleSetContext
		@Override
		public String getModelType() {
			return EmptyAnonymousModelRoleSetContextGetter.this.getModelType();
		}

		@Override
		public ModelRoleSet getRoleSet() {
			return EmptyModelRoleSetImpl.make();
		}

	}

}
