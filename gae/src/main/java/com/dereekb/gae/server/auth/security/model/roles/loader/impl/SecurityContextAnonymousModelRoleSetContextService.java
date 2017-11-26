package com.dereekb.gae.server.auth.security.model.roles.loader.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.EmptyLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.impl.EmptyModelRoleSetImpl;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextGetter;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextService;
import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.impl.UniqueModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AnonymousModelRoleSetContextGetter} implementation that uses the
 * {@link LoginSecurityContext} to retrieve results from the models context.
 *
 * @author dereekb
 *
 */
public class SecurityContextAnonymousModelRoleSetContextService
        implements AnonymousModelRoleSetContextService {

	// MARK: AnonymousModelRoleSetContextService
	@Override
	public AnonymousModelRoleSetContextGetter getterForType(String type) {
		LoginTokenModelContextSet set = this.getContextSet();

		if (set.hasContextForType(type)) {
			return new AnonymousModelRoleSetContextGetterImpl(type, set.getContextsForType(type));
		} else {
			return new EmptyAnonymousModelRoleSetContextGetter(type);
		}
	}

	protected LoginTokenModelContextSet getContextSet() {
		try {
			return LoginSecurityContext.getAuthentication().getPrincipal().getLoginTokenModelContextSet();
		} catch (NoSecurityContextException e) {
			return new EmptyLoginTokenModelContextSet();
		}
	}

	private class AnonymousModelRoleSetContextGetterImpl extends TypedModelImpl
	        implements AnonymousModelRoleSetContextGetter {

		private final LoginTokenTypedModelContextSet set;

		private transient Map<ModelKey, LoginTokenModelContext> map;

		public AnonymousModelRoleSetContextGetterImpl(String modelType, LoginTokenTypedModelContextSet set) {
			super(modelType);
			this.set = set;
		}

		// MARK: AnonymousModelRoleSetContextGetter
		@Override
		public List<? extends AnonymousModelRoleSetContext> getWithKeys(Iterable<ModelKey> keys) {
			List<AnonymousModelRoleSetContext> list = new ArrayList<AnonymousModelRoleSetContext>();

			for (ModelKey key : keys) {
				AnonymousModelRoleSetContext context = this.get(key);
				list.add(context);
			}

			return list;
		}

		@Override
		public AnonymousModelRoleSetContext get(ModelKey key) throws IllegalArgumentException {
			return new AnonymousModelRoleSetContextImpl(key);
		}

		// MARK: Internal
		protected Map<ModelKey, LoginTokenModelContext> getMap() {
			if (this.map == null) {
				this.map = ModelKey.makeModelKeyMap(this.set.getContexts());
			}

			return this.map;
		}

		private class AnonymousModelRoleSetContextImpl extends UniqueModelImpl
		        implements AnonymousModelRoleSetContext {

			private transient ModelRoleSet roleSet;

			public AnonymousModelRoleSetContextImpl(ModelKey key) {
				super(key);
			}

			// MARK: AnonymousModelRoleSetContext
			@Override
			public String getModelType() {
				return AnonymousModelRoleSetContextGetterImpl.this.getModelType();
			}

			@Override
			public ModelRoleSet getRoleSet() {
				if (this.roleSet == null) {
					LoginTokenModelContext context = AnonymousModelRoleSetContextGetterImpl.this.getMap()
					        .get(this.modelKey);

					if (context != null) {
						this.roleSet = context.getRoleSet();
					} else {
						this.roleSet = EmptyModelRoleSetImpl.make();
					}
				}

				return this.roleSet;
			}

		}

		@Override
		public String toString() {
			return "AnonymousModelRoleSetContextGetterImpl [modelType=" + this.modelType + "]";
		}

	}

}
