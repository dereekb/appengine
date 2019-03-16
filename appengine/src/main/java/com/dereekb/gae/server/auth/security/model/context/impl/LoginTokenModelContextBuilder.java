package com.dereekb.gae.server.auth.security.model.context.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.impl.EmptyModelRoleSetImpl;
import com.dereekb.gae.server.datastore.models.impl.AbstractUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Utility used for building {@link LoginTokenModelContext} values.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextBuilder {

	private final String modelType;

	public LoginTokenModelContextBuilder(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	public static Builder make(String modelType) {
		return new LoginTokenModelContextBuilder(modelType).roles();
	}

	public static Builder make(String modelType, ModelRoleSet roles) {
		return new LoginTokenModelContextBuilder(modelType).roles(roles);
	}

	public String getModelType() {
		return this.modelType;
	}

	// MARK: Builder
	protected Builder roles() {
		return this.roles(EmptyModelRoleSetImpl.make());
	}

	public Builder roles(ModelRoleSet roles) {
		return new BuilderImpl(roles);
	}

	public interface Builder {

		public List<LoginTokenModelContext> make(Iterable<ModelKey> keys);

		public LoginTokenModelContext make(ModelKey key);

	}

	protected class BuilderImpl
	        implements Builder {

		private final ModelRoleSet roles;

		public BuilderImpl(ModelRoleSet roles) {
			if (roles == null) {
				throw new IllegalArgumentException("roles cannot be null.");
			}

			this.roles = roles;
		}

		public ModelRoleSet getRoles() {
			return this.roles;
		}

		@Override
		public List<LoginTokenModelContext> make(Iterable<ModelKey> keys) {

			List<LoginTokenModelContext> list = new ArrayList<LoginTokenModelContext>();

			for (ModelKey key : keys) {
				list.add(this.make(key));
			}

			return list;
		}

		@Override
		public LoginTokenModelContext make(ModelKey key) {
			return new BuilderLoginTokenModelContext(key);
		}

		// MARK: Context
		protected class BuilderLoginTokenModelContext extends AbstractUniqueModel
		        implements LoginTokenModelContext {

			private ModelKey modelKey;

			public BuilderLoginTokenModelContext(ModelKey modelKey) {
				if (modelKey == null) {
					throw new IllegalArgumentException("modelKey cannot be null.");
				}

				this.modelKey = modelKey;
			}

			// MARK:
			@Override
			public String getModelType() {
				return LoginTokenModelContextBuilder.this.modelType;
			}

			@Override
			public ModelKey getModelKey() {
				return this.modelKey;
			}

			@Override
			public ModelRoleSet getRoleSet() {
				return BuilderImpl.this.roles;
			}

			@Override
			public String toString() {
				return "BuilderLoginTokenModelContext [modelKey=" + this.modelKey + "]";
			}

		}

		@Override
		public String toString() {
			return "Builder [roles=" + this.roles + "]";
		}

	}

}
