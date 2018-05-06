package com.dereekb.gae.client.api.auth.model.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.client.api.auth.model.ClientModelRolesContextService;
import com.dereekb.gae.client.api.auth.model.ClientModelRolesResponseData;
import com.dereekb.gae.client.api.auth.model.impl.ClientModelRolesRequestImpl;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceEntry;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetImpl;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.impl.UniqueModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.model.lazy.LazyLoadSource;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.lazy.impl.LazyLoadSourceImpl;
import com.dereekb.gae.utilities.model.source.Source;
import com.dereekb.gae.web.api.auth.controller.model.ApiLoginTokenModelContextType;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiLoginTokenModelContextTypeImpl;

/**
 * Factory for {@link ClientLoginTokenModelContextServiceEntryImpl} values.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenModelContextServiceEntryFactory {

	private static final Partitioner DEFAULT_PARTITIONER = new PartitionerImpl(20);

	private Partitioner keyPartitioner = DEFAULT_PARTITIONER;
	private TypeModelKeyConverter keyConverter;

	public TypeModelKeyConverter getKeyConverter() {
		return this.keyConverter;
	}

	public void setKeyConverter(TypeModelKeyConverter keyConverter) {
		if (keyConverter == null) {
			throw new IllegalArgumentException("keyConverter cannot be null.");
		}

		this.keyConverter = keyConverter;
	}

	// MARK: Make
	public LoginTokenModelContextServiceEntry makeServiceEntry(String modelType,
	                                                           ClientModelRolesContextService clientContextService) {
		return new ClientLoginTokenModelContextServiceEntryImpl(modelType, clientContextService);
	}

	// MARK: LoginTokenModelContextServiceEntry
	/**
	 * {@link LoginTokenModelContextServiceEntry} implementation.
	 *
	 * @author dereekb
	 *
	 */
	protected class ClientLoginTokenModelContextServiceEntryImpl extends TypedModelImpl
	        implements LoginTokenModelContextServiceEntry {

		private ClientModelRolesContextService clientContextService;

		public ClientLoginTokenModelContextServiceEntryImpl(String modelType,
		        ClientModelRolesContextService clientContextService) {
			super(modelType);
		}

		// MARK: LoginTokenModelContextServiceEntry
		@Override
		public AnonymousModelRoleSetContext getAnonymous(ModelKey key) throws IllegalArgumentException {
			return this.getAnonymousWithKeys(SingleItem.withValue(key)).get(0);
		}

		@Override
		public List<? extends AnonymousModelRoleSetContext> getAnonymousWithKeys(Iterable<ModelKey> keys) {
			return new ClientLoginTokenTypedModelContextSetImpl(false, IteratorUtility.iterableToSet(keys))
			        .getContexts();
		}

		@Override
		public LoginTokenTypedModelContextSet makeTypedContextSet(Set<String> keys,
		                                                          boolean atomic)
		        throws AtomicOperationException {
			List<ModelKey> modelKeys = ClientLoginTokenModelContextServiceEntryFactory.this.keyConverter
			        .convertKeys(this.getModelType(), keys);
			Set<ModelKey> keySet = new HashSet<ModelKey>(modelKeys);
			return new ClientLoginTokenTypedModelContextSetImpl(atomic, keySet);
		}

		// Implementations
		private class ClientLoginTokenModelContextImpl extends UniqueModelImpl
		        implements LoginTokenModelContext {

			private Source<ModelRoleSet> setSource;

			public ClientLoginTokenModelContextImpl(ModelKey modelKey, Source<ModelRoleSet> setSource) {
				super(modelKey);
				this.setSource = setSource;
			}

			// MARK: LoginTokenModelContext
			@Override
			public String getModelType() {
				return ClientLoginTokenModelContextServiceEntryImpl.this.getModelType();
			}

			@Override
			public ModelRoleSet getRoleSet() {
				return this.setSource.loadObject();
			}

		}

		private class ClientLoginTokenTypedModelContextSetImpl
		        implements LoginTokenTypedModelContextSet {

			private boolean atomic;
			private Set<ModelKey> keys;

			private transient List<LoginTokenModelContext> contexts;

			public ClientLoginTokenTypedModelContextSetImpl(boolean atomic, Set<ModelKey> keys) {

				if (keys == null) {
					throw new IllegalArgumentException("keys cannot be null.");
				}

				this.keys = keys;
				this.atomic = atomic;
			}

			// MARK: LoginTokenTypedModelContextSet
			@Override
			public String getModelType() {
				return ClientLoginTokenModelContextServiceEntryImpl.this.getModelType();
			}

			@Override
			public List<LoginTokenModelContext> getContexts() {
				if (this.contexts == null) {
					this.contexts = this.buildContexts();
				}

				return this.contexts;
			}

			protected List<LoginTokenModelContext> buildContexts() {
				return new ClientLoginTokenModelContextLoader().getContextList();
			}

			private class ClientLoginTokenModelContextLoader {

				private final List<LoginTokenModelContext> contexts;

				private ClientLoginTokenModelContextLoader() {
					List<List<ModelKey>> modelKeys;

					if (ClientLoginTokenTypedModelContextSetImpl.this.atomic) {
						modelKeys = ListUtility
						        .wrap(new ArrayList<ModelKey>(ClientLoginTokenTypedModelContextSetImpl.this.keys));
					} else {
						modelKeys = ClientLoginTokenModelContextServiceEntryFactory.this.keyPartitioner
						        .makePartitions(ClientLoginTokenTypedModelContextSetImpl.this.keys);
					}

					List<ClientLoginTokenModelContextLoaderPartition> partitions = new ArrayList<ClientLoginTokenModelContextLoaderPartition>();

					List<LoginTokenModelContext> contexts = new ArrayList<LoginTokenModelContext>();

					for (List<ModelKey> keyList : modelKeys) {
						ClientLoginTokenModelContextLoaderPartition partition = new ClientLoginTokenModelContextLoaderPartition(
						        keyList);
						partitions.add(partition);
						contexts.addAll(partition.getContexts());
					}

					this.contexts = contexts;

					if (ClientLoginTokenTypedModelContextSetImpl.this.atomic) {
						partitions.get(0).load();
					}
				}

				public List<LoginTokenModelContext> getContextList() {
					return this.contexts;
				}

				private class ClientLoginTokenModelContextLoaderPartition {

					private final List<ModelKey> modelKeys;
					private final List<LoginTokenModelContext> contexts;

					private transient Map<ModelKey, Set<String>> rolesMap;
					private transient ClientModelRolesResponseData response;

					public ClientLoginTokenModelContextLoaderPartition(List<ModelKey> keyList) {
						this.modelKeys = keyList;
						this.contexts = this.buildContexts(keyList);
					}

					private List<LoginTokenModelContext> buildContexts(List<ModelKey> keyList) {
						List<LoginTokenModelContext> contexts = new ArrayList<LoginTokenModelContext>();

						for (ModelKey key : keyList) {
							ModelRoleSetSource loadRoleSource = new ModelRoleSetSource(key);
							LazyLoadSource<ModelRoleSet> source = new LazyLoadSourceImpl<ModelRoleSet>(loadRoleSource);
							LoginTokenModelContext context = new ClientLoginTokenModelContextImpl(key, source);
							contexts.add(context);
						}

						return contexts;
					}

					private List<LoginTokenModelContext> getContexts() {
						return this.contexts;
					}

					public Map<ModelKey, Set<String>> getRolesMap() {
						this.load();
						return this.rolesMap;
					}

					public void load() {
						if (this.response == null) {

							// TODO: This piece isn't thread-safe, although
							// threads will most likely never be sharing the
							// same ContextSet.

							this.response = this.performRequest();
							this.rolesMap = this.response
							        .getRolesForType(ClientLoginTokenModelContextServiceEntryImpl.this.modelType);
						}
					}

					protected ClientModelRolesResponseData performRequest() {
						ApiLoginTokenModelContextType context = ApiLoginTokenModelContextTypeImpl
						        .fromKeyed(getModelType(), this.modelKeys);
						ClientModelRolesRequestImpl request = new ClientModelRolesRequestImpl(context);
						request.setAtomic(ClientLoginTokenTypedModelContextSetImpl.this.atomic);

						try {
							ClientModelRolesResponseData response = ClientLoginTokenModelContextServiceEntryImpl.this.clientContextService
							        .getRolesForModels(request, ClientRequestSecurityImpl.current());
							return response;
						} catch (ClientAtomicOperationException e) {
							throw new AtomicOperationException(e.getMissingKeys(), e);
						} catch (ClientRequestFailureException e) {
							throw new RuntimeException(e);
						}
					}

					private class ModelRoleSetSource
					        implements Source<ModelRoleSet> {

						private final ModelKey key;

						public ModelRoleSetSource(ModelKey key) {
							this.key = key;
						}

						// MARK: Source
						@Override
						public ModelRoleSet loadObject() throws RuntimeException, UnavailableSourceObjectException {
							Set<String> roles = getRolesMap().get(this.key);
							return ModelRoleSetImpl.makeWithStringRoles(roles);
						}

					}

				}

			}

		}

	}

}
