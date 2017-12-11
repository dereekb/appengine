package com.dereekb.gae.server.auth.security.model.roles.utility.crud;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.googlecode.objectify.Key;

/**
 * Abstract attribute utility that can create instances for specific attributes on models.
 *
 * @author dereekb
 *
 * @param <T> model type
 */
public abstract class AbstractModelRoleAttributeUtility<T extends UniqueModel> {

	public static final String NO_MODEL_CODE = "NO_MODEL";
	public static final String NO_PERMISSION_CODE = "NO_PERMISSION";
	public static final String MODEL_UNAVAILABLE_CODE = "MODEL_UNAVAILABLE";

	private ModelRoleSetContextService<T> modelRoleSetContextService;

	public AbstractModelRoleAttributeUtility(ModelRoleSetContextService<T> modelRoleSetContextService) {
		this.setModelRoleSetContextService(modelRoleSetContextService);
	}

	public ModelRoleSetContextService<T> getModelRoleSetContextService() {
		return this.modelRoleSetContextService;
	}

	public void setModelRoleSetContextService(ModelRoleSetContextService<T> modelRoleSetContextService) {
		if (modelRoleSetContextService == null) {
			throw new IllegalArgumentException("modelRoleSetContextService cannot be null.");
		}

		this.modelRoleSetContextService = modelRoleSetContextService;
	}

	// MARK: Instance
	protected AttributeInstanceImpl makeInstanceWithKey(String attribute, Key<T> key) throws InvalidAttributeException {
		this.assertKeyIsNotNull(attribute, key);
		return new AttributeInstanceImpl(attribute, key);
	}

	protected void assertKeyIsNotNull(String attribute, Key<?> key) {
		if (key == null) {
			throw new InvalidAttributeException(attribute, null, "No model was provided in the request.", NO_MODEL_CODE);
		}
	}

	public class AttributeInstanceImpl implements ModelRoleAttributeInstance<T> {

		protected final String attribute;
		protected final Key<T> key;
		protected final transient ModelKey modelKey;

		private transient ModelRoleSetContext<T> context;

		protected AttributeInstanceImpl(String attribute, Key<T> key) {
			this.attribute = attribute;
			this.key = key;
			this.modelKey = ObjectifyModelKeyUtil.readModelKey(this.key);
		}

		@Override
		public Key<T> getKey() {
			return this.key;
		}

		public T getModel() throws InvalidAttributeException {
			return this.getContext().getModel();
		}

		@Override
		public void assertExists() throws InvalidAttributeException {
			this.getContext();
		}

		@Override
		public void assertHasRole(ModelRole role) throws InvalidAttributeException {
			ModelRoleSetContext<T> context = this.getContext();
			ModelRoleSet roleSet = context.getRoleSet();

			if (!roleSet.hasRole(role)) {
				throw new InvalidAttributeException(this.attribute, role.getRole(),
				        "You lack the proper permissions.", NO_PERMISSION_CODE);
			}
		}

		// MARK: Accessors
		@Override
		public ModelRoleSetContext<T> getContext() throws InvalidAttributeException {
			if (this.context == null) {
				this.context = AbstractModelRoleAttributeUtility.this.modelRoleSetContextService.get(this.modelKey);

				if (this.context == null) {
					throw this.makeModelUnavailableException();
				}
			}

			return this.context;
		}

		protected InvalidAttributeException makeModelUnavailableException() {
			return new InvalidAttributeException(this.attribute, this.modelKey, "The requested model is unavailable.",
			        MODEL_UNAVAILABLE_CODE);
		}

	}

}
