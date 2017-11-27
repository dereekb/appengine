package com.dereekb.gae.server.auth.security.model.context.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.components.impl.ReadServiceImpl;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.task.ReadTask;
import com.dereekb.gae.model.crud.task.impl.ReadTaskImpl;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderEntryImpl;
import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenModelContextBuilder;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenTypedModelContextSetImpl;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceEntry;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.encoded.ModelRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.auth.security.model.roles.loader.impl.ModelRoleSetContextServiceImpl;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;

/**
 * {@link LoginTokenModelContextServiceEntry} implementation that extends
 * {@link LoginTokenModelContextSetEncoderDecoderEntryImpl}.
 * <p>
 * Also implements {@link ModelRoleSetContextService}.
 *
 * @author dereekb
 *
 */
public class LoginTokenModelContextServiceEntryImpl<T extends UniqueModel> extends LoginTokenModelContextSetEncoderDecoderEntryImpl
        implements LoginTokenModelContextServiceEntry, ModelRoleSetContextService<T> {

	private Getter<T> getter;
	private ModelRoleSetLoader<T> rolesLoader;

	private transient ReadTask<T> readTask;
	private transient ModelRoleSetContextService<T> roleSetService;

	public LoginTokenModelContextServiceEntryImpl(String modelType,
	        ModelKeyType keyType,
	        ModelRoleSetEncoderDecoder rolesDencoder,
	        Getter<T> getter,
	        ModelRoleSetLoader<T> rolesLoader) {
		super(modelType, keyType, rolesDencoder);
		this.setGetter(getter);
		this.setRolesLoader(rolesLoader);
	}

	public LoginTokenModelContextServiceEntryImpl(String modelType,
	        StringModelKeyConverter keyConverter,
	        ModelRoleSetEncoderDecoder rolesDencoder,
	        Getter<T> getter,
	        ModelRoleSetLoader<T> rolesLoader) {
		super(modelType, keyConverter, rolesDencoder);
		this.setGetter(getter);
		this.setRolesLoader(rolesLoader);
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
		this.readTask = new ReadTaskImpl<T>(this.getter);
		this.resetRoleSetService();
	}

	public ModelRoleSetLoader<T> getRolesLoader() {
		return this.rolesLoader;
	}

	public void setRolesLoader(ModelRoleSetLoader<T> rolesLoader) {
		if (rolesLoader == null) {
			throw new IllegalArgumentException("rolesLoader cannot be null.");
		}

		this.rolesLoader = rolesLoader;
		this.resetRoleSetService();
	}

	protected void resetRoleSetService() {
		if (this.getter != null && this.rolesLoader != null) {
			this.roleSetService = new ModelRoleSetContextServiceImpl<T>(this.rolesLoader, this.getter);
		}
	}

	// MARK: LoginTokenModelContextServiceEntry
	@Override
	public LoginTokenTypedModelContextSet makeTypedContextSet(Set<String> keys,
	                                                          boolean atomic)
	        throws AtomicOperationException {

		List<ModelKey> modelKeys = this.getKeyConverter().convert(keys);
		ReadResponse<T> readResponse = ReadServiceImpl.doReadForModels(this.readTask, modelKeys, atomic);

		Collection<T> models = readResponse.getModels();
		return this.makeInstanceForModels(models, atomic).makeTypedModelContextSet();
	}

	protected Instance makeInstanceForModels(Collection<T> models,
	                                         boolean atomic) {
		return new Instance(models, atomic);
	}

	protected class Instance {

		protected final Collection<T> models;
		protected final boolean atomic;

		private final LoginTokenModelContextBuilder builder;

		public Instance(Collection<T> models, boolean atomic) {
			super();
			this.models = models;
			this.atomic = atomic;
			this.builder = new LoginTokenModelContextBuilder(
			        LoginTokenModelContextServiceEntryImpl.this.getModelType());
		}

		// MARK: LoginTokenTypedModelContextSet
		public LoginTokenTypedModelContextSet makeTypedModelContextSet() {

			List<LoginTokenModelContext> contexts = new ArrayList<LoginTokenModelContext>();

			for (T model : this.models) {
				try {
					LoginTokenModelContext context = this.makeContextForModel(model);
					contexts.add(context);
				} catch (NoModelContextRolesGrantedException e) {

					// If atomic, fail for being granted no roles.
					if (this.atomic) {
						throw new AtomicOperationException(model, AtomicOperationExceptionReason.UNAVAILABLE);
					}
				}
			}

			String modelType = LoginTokenModelContextServiceEntryImpl.this.getModelType();
			return new LoginTokenTypedModelContextSetImpl(modelType, contexts);
		}

		protected LoginTokenModelContext makeContextForModel(T model) throws NoModelContextRolesGrantedException {
			ModelRoleSet roleSet = this.loadRolesForModel(model);

			// No roles means remove from use.
			if (roleSet.isEmpty()) {
				throw new NoModelContextRolesGrantedException();
			}

			return this.builder.roles(roleSet).make(model.getModelKey());
		}

		protected ModelRoleSet loadRolesForModel(T model) {
			return LoginTokenModelContextServiceEntryImpl.this.rolesLoader.loadRolesForModel(model);
		}

	}

	// MARK: ModelRoleSetContextService
	@Override
	public ModelRoleSetContext<T> loadContext(T model) throws NoModelContextRolesGrantedException {
		return this.roleSetService.loadContext(model);
	}

	@Override
	public List<ModelRoleSetContext<T>> loadContexts(Iterable<T> models) {
		return this.roleSetService.loadContexts(models);
	}

	@Override
	public ModelRoleSetContext<T> get(ModelKey key)
	        throws IllegalArgumentException,
	            NoModelContextRolesGrantedException {
		return this.roleSetService.get(key);
	}

	@Override
	public List<ModelRoleSetContext<T>> getWithKeys(Iterable<ModelKey> keys) {
		return this.roleSetService.getWithKeys(keys);
	}

	@Override
	public AnonymousModelRoleSetContext getAnonymous(ModelKey key) throws IllegalArgumentException {
		return this.roleSetService.getAnonymous(key);
	}

	@Override
	public List<? extends AnonymousModelRoleSetContext> getAnonymousWithKeys(Iterable<ModelKey> keys) {
		return this.roleSetService.getAnonymousWithKeys(keys);
	}

	@Override
	public ModelRoleSet loadRolesForModel(T model) throws NoModelContextRolesGrantedException {
		return this.roleSetService.loadRolesForModel(model);
	}

	@Override
	public String toString() {
		return "LoginTokenModelContextServiceEntryImpl [getter=" + this.getter + ", rolesLoader=" + this.rolesLoader
		        + ", getModelType()=" + this.getModelType() + ", getKeyConverter()=" + this.getKeyConverter()
		        + ", getRolesDencoder()=" + this.getRolesDencoder() + "]";
	}

}
