package com.dereekb.gae.server.datastore.objectify.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;
import com.dereekb.gae.server.datastore.exception.UninitializedModelException;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.LoadedModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.ModelKeyListAccessorImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryModelResultIterator;
import com.dereekb.gae.server.datastore.models.query.impl.AbstractIndexedModelQueryKeyResponse;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterable;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistryFactory;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedSetter;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcer;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityReader;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityWriter;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifySource;
import com.dereekb.gae.server.datastore.objectify.core.exception.UnregisteredEntryTypeException;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyUtility;
import com.dereekb.gae.server.datastore.objectify.keys.IllegalKeyConversionException;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryKeyResponse;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryModelResponse;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyKeyInSetFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryModelResultIteratorImpl;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestBuilderImpl;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.server.datastore.objectify.query.iterator.impl.ObjectifyQueryIterableFactoryImpl;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.utility.GetterUtility;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.KeyRange;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.QueryKeys;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * Objectify Database Singleton used for performing changes using Objectify.
 *
 * @author dereekb
 *
 */
public class ObjectifyDatabaseImpl
        implements ObjectifyDatabase, ObjectifyRegistryFactory {

	private Map<String, Class<?>> aliases = new HashMap<>();
	private Map<Class<?>, ObjectifyDatabaseEntityDefinition> definitions = new HashMap<>();

	public ObjectifyDatabaseImpl(Iterable<ObjectifyDatabaseEntityDefinition> entities) {
		super();
		this.addEntityDefinitions(entities);
	}

	private void addEntityDefinitions(Iterable<ObjectifyDatabaseEntityDefinition> entities) {
		ObjectifyFactory factory = ObjectifyService.factory();

		for (ObjectifyDatabaseEntityDefinition entity : entities) {
			Class<?> type = entity.getEntityType();
			String alias = entity.getEntityName();

			factory.register(type); // Register with Objectify

			this.aliases.put(alias, type);
			this.definitions.put(type, entity);
		}
	}

	public Set<Class<?>> getDefinitionTypes() {
		return this.definitions.keySet();
	}

	// ObjectifyRegistryFactory
	@Override
	public <T extends ObjectifyModel<T>> ObjectifyRegistry<T> makeRegistry(Class<T> type) {
		return this.getDatabaseEntity(type);
	}

	// Objectify
	@Override
	public Objectify ofy() {
		return ObjectifyService.ofy();
	}

	// Objectify Factory
	@Override
	public <T extends ObjectifyModel<T>> ObjectifyDatabaseEntity<T> getDatabaseEntity(Class<T> type) {
		return new ObjectifyDatabaseEntityImpl<T>(type);
	}

	private ObjectifyDatabaseEntityDefinition getDefinition(Class<?> type) throws UnregisteredEntryTypeException {
		ObjectifyDatabaseEntityDefinition entity = this.definitions.get(type);

		if (entity == null) {
			throw new UnregisteredEntryTypeException(type);
		}

		return entity;
	}

	/**
	 * Internal {@link ObjectifyDatabaseEntity} implementation.
	 *
	 * @author dereekb
	 *
	 * @param <T>
	 *            model type
	 */
	protected class ObjectifyDatabaseEntityImpl<T extends ObjectifyModel<T>>
	        implements ObjectifyKeyConverter<T, ModelKey>, ObjectifyDatabaseEntity<T> {

		private final Class<T> type;
		private final String modelTypeName;
		private final ModelKeyType keyType;

		private final ObjectifyKeyConverter<T, ModelKey> objectifyKeyConverter;
		private final ObjectifyQueryRequestLimitedBuilderInitializer initializer;
		private final ObjectifyQueryIterableFactory<T> iterableFactory;

		private final ObjectifyDatabaseEntityKeyEnforcement keyEnforcement;
		private final ObjectifyDatabaseEntityKeyEnforcer keyEnforcer;

		protected ObjectifyDatabaseEntityImpl(Class<T> type) throws UnregisteredEntryTypeException {
			ObjectifyDatabaseEntityDefinition entity = ObjectifyDatabaseImpl.this.getDefinition(type);

			this.type = type;
			this.modelTypeName = entity.getEntityName();
			this.keyType = entity.getEntityKeyType();
			this.objectifyKeyConverter = ObjectifyModelKeyUtil.converterForType(type, this.keyType);
			this.initializer = entity.getQueryInitializer();
			this.iterableFactory = new ObjectifyQueryIterableFactoryImpl<T>(this);

			this.keyEnforcement = entity.getKeyEnforcement();
			this.keyEnforcer = this.makeKeyEnforcer(this.keyEnforcement);

			this.resetAccessors();
		}

		public Class<T> getType() {
			return this.type;
		}

		@Override
		public String getModelType() {
			return this.modelTypeName;
		}

		public ModelKeyType getKeyType() {
			return this.keyType;
		}

		@Override
		public ObjectifyKeyConverter<T, ModelKey> getObjectifyKeyConverter() {
			return this.objectifyKeyConverter;
		}

		protected void resetAccessors() {
			this.resetReaderWriter();
			this.resetGetterSetters();
		}

		protected void resetReaderWriter() {
			this.reader = new ObjectifyDatabaseEntityReaderImpl();
			this.writer = new ObjectifyDatabaseEntityWriterImpl();
		}

		protected void resetGetterSetters() {
			this.getter = new ObjectifyDatabaseGetter();
			this.setter = new ObjectifyDatabaseSetter();
		}

		@Override
		public ObjectifyDatabaseEntityKeyEnforcement getKeyEnforcement() {
			return this.keyEnforcement;
		}

		private ObjectifyDatabaseEntityKeyEnforcer makeKeyEnforcer(ObjectifyDatabaseEntityKeyEnforcement keyEnforcement) {
			ObjectifyDatabaseEntityKeyEnforcer keyEnforcer = null;

			switch (keyEnforcement) {
				case DEFAULT:
				case MUST_BE_NULL:
				case MUST_BE_PROVIDED:
				case MUST_BE_PROVIDED_AND_UNIQUE:
					keyEnforcer = ObjectifyDatabaseEntityKeyEnforcerUtility.enforcerForType(keyEnforcement, this);
					break;
				default:
					throw new UnsupportedOperationException();
			}

			return keyEnforcer;
		}

		// MARK: ObjectifyKeyAllocator
		@Override
		public Key<T> allocateId() {
			return ObjectifyDatabaseImpl.this.ofy().factory().allocateId(this.type);
		}

		@Override
		public KeyRange<T> allocateIds(int count) {
			if (count < 1) {
				throw new IllegalArgumentException("Count must be positive and non-zero.");
			}

			return ObjectifyDatabaseImpl.this.ofy().factory().allocateIds(this.type, count);
		}

		// MARK: ObjectifyDatabaseEntityReader
		private ObjectifyDatabaseEntityReader<T> reader;

		@Override
		public ObjectifyDatabaseEntityReader<T> getReader() {
			return this.reader;
		}

		protected void setReader(ObjectifyDatabaseEntityReader<T> reader) {
			if (reader == null) {
				throw new IllegalArgumentException("reader cannot be null.");
			}

			this.reader = reader;
		}

		/**
		 * @author dereekb
		 *
		 * @deprecated while reading keys in a case-insensitive manner is easy
		 *             enough, they also need to be written as a lower-case
		 *             value, which is not easy to do generically with the
		 *             current Objectify API since the entities are written with
		 *             the IDs that are attached.
		 */
		@Deprecated
		protected class CaseInsensitiveObjectifyDatabaseEntityReaderImpl extends ObjectifyDatabaseEntityReaderImpl {

			@Override
			public T get(Key<T> key) {
				return super.get(this.makeCaseInsensitiveKey(key));
			}

			@Override
			public List<T> keysGet(Iterable<Key<T>> list) {
				return super.keysGet(this.makeCaseInsensitiveKeys(list));
			}

			@Override
			public List<T> refsGet(Iterable<Ref<T>> list) {
				return super.keysGet(this.makeCaseInsensitiveFromRefs(list));
			}

			protected List<Key<T>> makeCaseInsensitiveFromRefs(Iterable<Ref<T>> input) {
				List<Key<T>> keys = new ArrayList<Key<T>>();

				for (Ref<T> key : input) {
					keys.add(this.makeCaseInsensitiveKey(key.getKey()));
				}

				return keys;
			}

			protected List<Key<T>> makeCaseInsensitiveKeys(Iterable<Key<T>> input) {
				List<Key<T>> keys = new ArrayList<Key<T>>();

				for (Key<T> key : input) {
					keys.add(this.makeCaseInsensitiveKey(key));
				}

				return keys;
			}

			@SuppressWarnings("unchecked")
			protected Key<T> makeCaseInsensitiveKey(Key<T> input) {
				String name = input.getName().toLowerCase();
				Class<T> type = (Class<T>) input.getClass();
				return Key.create(type, name);
			}

		}

		protected class ObjectifyDatabaseEntityReaderImpl
		        implements ObjectifyDatabaseEntityReader<T> {

			@Override
			public boolean exists(Key<T> key) {
				return (this.get(key) != null);
			}

			@Override
			public T get(Key<T> key) {
				Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
				return objectify.load().key(key).now();
			}

			@Override
			public List<T> keysGet(Iterable<Key<T>> list) {
				Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
				Map<Key<T>, T> results = objectify.load().keys(list);
				return new ArrayList<T>(results.values());
			}

			@Override
			public List<T> refsGet(Iterable<Ref<T>> list) {
				Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
				Map<Key<T>, T> results = objectify.load().refs(list);
				return new ArrayList<T>(results.values());
			}

			@Override
			public Query<T> makeQuery(boolean allowCache) {
				Objectify objectify = ObjectifyDatabaseImpl.this.ofy().cache(allowCache);
				Query<T> query = objectify.load().type(ObjectifyDatabaseEntityImpl.this.type);
				return query;
			}

		}

		// MARK: ObjectifyDatabaseEntityModifier
		protected ObjectifyDatabaseEntityWriter<T> writer;

		@Override
		public ObjectifyDatabaseEntityWriter<T> getWriter() {
			return this.writer;
		}

		protected class ObjectifyDatabaseEntityWriterImpl
		        implements ObjectifyDatabaseEntityWriter<T> {

			private final ObjectifySource source;

			public ObjectifyDatabaseEntityWriterImpl() {
				this(ObjectifyDatabaseImpl.this);
			}

			public ObjectifyDatabaseEntityWriterImpl(ObjectifySource source) {
				this.source = source;
			}

			// MARK: Put
			@Override
			public Result<Key<T>> put(T entity) {
				if (entity == null) {
					throw new IllegalArgumentException();
				}

				Objectify objectify = this.source.ofy();
				return objectify.save().entity(entity);
			}

			@Override
			public Result<Map<Key<T>, T>> put(Iterable<T> entities) {
				if (entities == null) {
					throw new IllegalArgumentException();
				}

				Objectify objectify = this.source.ofy();
				return objectify.save().entities(entities);
			}

			// MARK: Delete
			@Override
			public Result<Void> delete(T entity) {
				if (entity == null) {
					throw new IllegalArgumentException();
				}

				Objectify objectify = this.source.ofy();
				return objectify.delete().entity(entity);
			}

			@Override
			public Result<Void> deleteWithKey(Key<T> key) {
				if (key == null) {
					throw new IllegalArgumentException();
				}

				Objectify objectify = this.source.ofy();
				return objectify.delete().key(key);
			}

			@Override
			public Result<Void> delete(Iterable<T> list) {
				if (list == null) {
					throw new IllegalArgumentException();
				}

				Objectify objectify = this.source.ofy();
				return objectify.delete().entities(list);
			}

			@Override
			public Result<Void> deleteWithKeys(Iterable<Key<T>> list) {
				if (list == null) {
					throw new IllegalArgumentException();
				}

				Objectify objectify = this.source.ofy();
				return objectify.delete().keys(list);
			}

		}

		// MARK: Getter
		protected ObjectifyDatabaseGetter getter;

		@Override
		public ObjectifyKeyedGetter<T> getter() {
			return this.getter;
		}

		protected void setGetter(ObjectifyDatabaseGetter getter) {
			if (getter == null) {
				throw new IllegalArgumentException("getter cannot be null.");
			}

			this.getter = getter;
		}

		protected class ObjectifyDatabaseGetter
		        implements ObjectifyKeyedGetter<T> {

			private ObjectifyDatabaseEntityReader<T> reader;

			public ObjectifyDatabaseGetter() {
				this(ObjectifyDatabaseEntityImpl.this.getReader());
			}

			public ObjectifyDatabaseGetter(ObjectifyDatabaseEntityReader<T> reader) {
				this.setReader(reader);
			}

			public ObjectifyDatabaseEntityReader<T> getReader() {
				return this.reader;
			}

			public void setReader(ObjectifyDatabaseEntityReader<T> reader) {
				if (reader == null) {
					throw new IllegalArgumentException("reader cannot be null.");
				}

				this.reader = reader;
			}

			// MARK: Getter
			@Override
			public boolean exists(T model) throws UninitializedModelException {
				ModelKey key = model.getModelKey();

				if (key == null) {
					throw new UninitializedModelException();
				}

				return this.exists(key);
			}

			@Override
			public boolean exists(ModelKey key) throws IllegalArgumentException {
				if (key == null) {
					throw new IllegalArgumentException();
				}

				Key<T> objectifyKey = ObjectifyDatabaseEntityImpl.this.objectifyKeyConverter.writeKey(key);
				return this.reader.exists(objectifyKey);
			}

			@Override
			public boolean allExist(Iterable<ModelKey> keys) throws IllegalArgumentException {
				Set<ModelKey> modelKeys = IteratorUtility.iterableToSet(keys);
				List<T> models = this.getWithKeys(modelKeys);
				return (modelKeys.size() == models.size());
			}

			@Override
			public Set<ModelKey> getExisting(Iterable<ModelKey> keys) throws IllegalArgumentException {
				List<T> models = this.getWithKeys(keys);
				List<ModelKey> keyList = ModelKey.readModelKeys(models);
				return new HashSet<ModelKey>(keyList);
			}

			@Override
			public T get(ModelKey key) throws IllegalArgumentException {
				Key<T> objectifyKey = ObjectifyDatabaseEntityImpl.this.objectifyKeyConverter.writeKey(key);
				return this.reader.get(objectifyKey);
			}

			@Override
			public T get(T model) throws UninitializedModelException {
				Key<T> key = model.getObjectifyKey();

				if (key == null) {
					throw new UninitializedModelException();
				}

				return this.reader.get(key);
			}

			@Override
			public List<T> get(Iterable<T> models) throws UninitializedModelException {
				List<Key<T>> keys = ObjectifyUtility.readKeys(models);
				return this.reader.keysGet(keys);
			}

			@Override
			public List<T> getWithKeys(Iterable<ModelKey> keys) {
				List<ModelKey> modelKeys = IteratorUtility.iterableToList(keys);
				List<Key<T>> objectifyKeys = ObjectifyDatabaseEntityImpl.this.objectifyKeyConverter
				        .convertFrom(modelKeys);
				return this.reader.keysGet(objectifyKeys);
			}

			// MARK: Objectify Keyed Getter
			@Override
			public boolean exists(Key<T> key) {
				return this.reader.exists(key);
			}

			@Override
			public T get(Key<T> key) {
				return this.reader.get(key);
			}

			@Override
			public List<T> getWithObjectifyKeys(Iterable<Key<T>> keys) {
				return this.reader.keysGet(keys);
			}

			@Override
			public String getModelType() {
				return ObjectifyDatabaseEntityImpl.this.getModelType();
			}

		}

		@Override
		public boolean exists(Key<T> key) {
			return this.getter.exists(key);
		}

		@Override
		public T get(Key<T> key) {
			return this.getter.get(key);
		}

		@Override
		public List<T> getWithObjectifyKeys(Iterable<Key<T>> keys) {
			return this.getter.getWithObjectifyKeys(keys);
		}

		@Override
		public boolean exists(T model) throws UninitializedModelException {
			return this.getter.exists(model);
		}

		@Override
		public boolean exists(ModelKey key) throws IllegalArgumentException {
			return this.getter.exists(key);
		}

		@Override
		public boolean allExist(Iterable<ModelKey> keys) throws IllegalArgumentException {
			return this.getter.allExist(keys);
		}

		@Override
		public Set<ModelKey> getExisting(Iterable<ModelKey> keys) throws IllegalArgumentException {
			return this.getter.getExisting(keys);
		}

		@Override
		public T get(ModelKey key) throws IllegalArgumentException {
			return this.getter.get(key);
		}

		@Override
		public T get(T model) throws UninitializedModelException {
			return this.getter.get(model);
		}

		@Override
		public List<T> get(Iterable<T> models) throws UninitializedModelException {
			return this.getter.get(models);
		}

		@Override
		public List<T> getWithKeys(Iterable<ModelKey> keys) {
			return this.getter.getWithKeys(keys);
		}

		// MARK: Setter
		protected ObjectifyDatabaseSetter setter;

		@Override
		public ObjectifyKeyedSetter<T> setter() {
			return this.setter;
		}

		protected class ObjectifyDatabaseSetter
		        implements ObjectifyKeyedSetter<T> {

			private Getter<T> getter = ObjectifyDatabaseEntityImpl.this;
			private ObjectifyDatabaseEntityWriter<T> source;

			public ObjectifyDatabaseSetter() {
				this(ObjectifyDatabaseEntityImpl.this.getWriter());
			}

			public ObjectifyDatabaseSetter(ObjectifyDatabaseEntityWriter<T> source) {
				this.setSource(source);
			}

			public ObjectifyDatabaseEntityWriter<T> getSource() {
				return this.source;
			}

			public void setSource(ObjectifyDatabaseEntityWriter<T> source) {
				if (source == null) {
					throw new IllegalArgumentException("source cannot be null.");
				}

				this.source = source;
			}

			// MARK: Updater
			@Override
			public boolean update(T entity) throws UpdateUnkeyedEntityException {
				return this.update(entity, false);
			}

			@Override
			public List<T> update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
				return this.update(entities, false);
			}

			@Override
			public boolean updateAsync(T entity) throws UpdateUnkeyedEntityException {
				return this.update(entity, true);
			}

			@Override
			public List<T> updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
				return this.update(entities, true);
			}

			protected boolean update(T entity,
			                         boolean async)
			        throws UpdateUnkeyedEntityException {
				if (entity.getModelKey() != null) {
					if (this.getter.exists(entity)) {
						Result<?> result = this.source.put(entity);

						if (!async) {
							result.now();
						}

						return true;
					}

					return false;
				} else {
					throw new UpdateUnkeyedEntityException();
				}
			}

			protected List<T> update(Iterable<T> entities,
			                         boolean async)
			        throws UpdateUnkeyedEntityException {
				for (T entity : entities) {
					if (entity.getModelKey() == null) {
						throw new UpdateUnkeyedEntityException();
					}
				}

				GetterUtility<T> utility = new GetterUtility<T>(this.getter);
				List<T> existing = utility.filterExisting(entities);
				Result<?> result = this.source.put(existing);

				if (!async) {
					result.now();
				}

				return existing;
			}

			// MARK: Storer
			@Override
			public void store(T entity) throws StoreKeyedEntityException {
				this.assertEntityIsAllowedForStore(entity);
				this.source.put(entity).now();
			}

			@Override
			public void store(Iterable<T> entities) throws StoreKeyedEntityException {
				for (T entity : entities) {
					this.assertEntityIsAllowedForStore(entity);
				}

				this.source.put(entities).now();
			}

			@Override
			public void forceStore(T entity) {
				this.source.put(entity).now();
			}

			@Override
			public void forceStore(Iterable<T> entities) {
				this.source.put(entities).now();
			}

			private void assertEntityIsAllowedForStore(T entity) {
				ModelKey key = entity.getModelKey();

				if (this.keyIsAllowedForStorage(key) == false) {
					throw new StoreKeyedEntityException(entity);
				}
			}

			private boolean keyIsAllowedForStorage(ModelKey key) {
				return ObjectifyDatabaseEntityImpl.this.keyEnforcer.isAllowedForStorage(key);
			}

			// MARK: Deleter
			@Override
			public void delete(T entity) {
				this.delete(entity, false);
			}

			@Override
			public void delete(Iterable<T> entities) {
				this.delete(entities, false);
			}

			@Override
			public void deleteAsync(T entity) {
				this.delete(entity, true);
			}

			@Override
			public void deleteAsync(Iterable<T> entities) {
				this.delete(entities, true);
			}

			@Override
			public void deleteWithKey(ModelKey key) {
				this.deleteWithKey(key, false);
			}

			@Override
			public void deleteWithKeys(Iterable<ModelKey> keys) {
				this.deleteWithKeys(keys, false);
			}

			@Override
			public void deleteWithKeyAsync(ModelKey key) {
				this.deleteWithKey(key, true);
			}

			@Override
			public void deleteWithKeysAsync(Iterable<ModelKey> keys) {
				this.deleteWithKeys(keys, true);
			}

			protected void delete(T entity,
			                      boolean async) {
				Result<Void> result = this.source.delete(entity);

				if (!async) {
					result.now();
				}
			}

			protected void delete(Iterable<T> entities,
			                      boolean async) {
				Result<Void> result = this.source.delete(entities);

				if (!async) {
					result.now();
				}
			}

			protected void deleteWithKey(ModelKey key,
			                             boolean async) {
				Key<T> objectifyKey = ObjectifyDatabaseEntityImpl.this.objectifyKeyConverter.writeKey(key);
				this.delete(objectifyKey, async);
			}

			protected void deleteWithKeys(Iterable<ModelKey> keys,
			                              boolean async) {
				List<Key<T>> objectifyKeys = ObjectifyDatabaseEntityImpl.this.objectifyKeyConverter.writeKeys(keys);
				this.deleteWithObjectifyKeys(objectifyKeys, async);
			}

			// MARK: ObjectifyDeleter

			@Override
			public void delete(Key<T> key) {
				this.delete(key, false);
			}

			@Override
			public void deleteWithObjectifyKeys(Iterable<Key<T>> keys) {
				this.deleteWithObjectifyKeys(keys, false);
			}

			@Override
			public void deleteAsync(Key<T> key) {
				this.delete(key, true);
			}

			@Override
			public void deleteWithObjectifyKeysAsync(Iterable<Key<T>> keys) {
				this.deleteWithObjectifyKeys(keys, true);
			}

			protected void delete(Key<T> key,
			                      boolean async) {
				Result<Void> result = this.source.deleteWithKey(key);

				if (!async) {
					result.now();
				}
			}

			protected void deleteWithObjectifyKeys(Iterable<Key<T>> keys,
			                                       boolean async) {
				Result<Void> result = this.source.deleteWithKeys(keys);

				if (!async) {
					result.now();
				}
			}

		}

		@Override
		public void delete(Key<T> key) {
			this.setter.delete(key);
		}

		@Override
		public void deleteWithObjectifyKeys(Iterable<Key<T>> keys) {
			this.setter.deleteWithObjectifyKeys(keys);
		}

		@Override
		public void deleteAsync(Key<T> key) {
			this.setter.delete(key);
		}

		@Override
		public void deleteWithObjectifyKeysAsync(Iterable<Key<T>> keys) {
			this.setter.deleteWithObjectifyKeys(keys);
		}

		@Override
		public void deleteAsync(T entity) {
			this.setter.deleteAsync(entity);
		}

		@Override
		public void deleteAsync(Iterable<T> entities) {
			this.setter.deleteAsync(entities);
		}

		@Override
		public boolean update(T entity) throws UpdateUnkeyedEntityException {
			return this.setter.update(entity);
		}

		@Override
		public List<T> update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
			return this.setter.update(entities);
		}

		@Override
		public boolean updateAsync(T entity) throws UpdateUnkeyedEntityException {
			return this.setter.updateAsync(entity);
		}

		@Override
		public List<T> updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
			return this.setter.update(entities);
		}

		@Override
		public void store(T entity) throws StoreKeyedEntityException {
			this.setter.store(entity);
		}

		@Override
		public void store(Iterable<T> entities) throws StoreKeyedEntityException {
			this.setter.store(entities);
		}

		@Override
		public void forceStore(T entity) {
			this.setter.forceStore(entity);
		}

		@Override
		public void forceStore(Iterable<T> entities) {
			this.setter.forceStore(entities);
		}

		@Override
		public void delete(T entity) {
			this.setter.delete(entity);
		}

		@Override
		public void delete(Iterable<T> entities) {
			this.setter.delete(entities);
		}

		@Override
		public void deleteWithKey(ModelKey key) {
			this.setter.deleteWithKey(key);
		}

		@Override
		public void deleteWithKeys(Iterable<ModelKey> keys) {
			this.setter.deleteWithKeys(keys);
		}

		@Override
		public void deleteWithKeyAsync(ModelKey key) {
			this.setter.deleteWithKey(key);
		}

		@Override
		public void deleteWithKeysAsync(Iterable<ModelKey> keys) {
			this.setter.deleteWithKeys(keys);
		}

		// MARK: Queries
		@Override
		public ObjectifyQueryIterableFactory<T> makeIterableQueryFactory() {
			return this.iterableFactory;
		}

		@Override
		public ObjectifyQueryModelResponse<T> query(ObjectifyQueryRequest<T> request) {
			ObjectifyQueryRequestExecutor executor = new ObjectifyQueryRequestExecutor(request);
			return executor.query();
		}

		@Override
		public Boolean modelsExist(Collection<Key<T>> keys) {
			Set<Key<T>> keysSet = new HashSet<Key<T>>(keys);
			Set<Key<T>> existingModels = this.filterExistingModels(keys);
			return keysSet.containsAll(existingModels);
		}

		@Override
		public Set<Key<T>> filterExistingModels(Collection<Key<T>> keys) {
			ObjectifyQueryRequestBuilder<T> builder = this.makeQuery();
			ObjectifyKeyInSetFilter<T> keysFilter = new ObjectifyKeyInSetFilter<T>(keys);
			builder.addSimpleQueryFilter(keysFilter);
			builder.buildExecutableQuery();

			List<Key<T>> existing = this.query(builder).queryObjectifyKeys();
			return new HashSet<Key<T>>(existing);
		}

		@Override
		public ObjectifyQueryRequestBuilder<T> makeQuery() {
			return new ObjectifyQueryRequestBuilderImpl<T>(this);
		}

		@Override
		public ObjectifyQueryRequestBuilder<T> makeQuery(Map<String, String> parameters)
		        throws IllegalQueryArgumentException {
			ObjectifyQueryRequestBuilder<T> builder = this.makeQuery();

			if (this.initializer != null && parameters != null) {
				this.initializer.initalizeBuilder(builder, parameters);
			}

			return builder;
		}

		@Override
		public ModelKeyListAccessor<T> createAccessor() {
			return new ModelKeyListAccessorImpl<T>(this.modelTypeName, this);
		}

		@Override
		public ModelKeyListAccessor<T> createAccessor(Collection<ModelKey> keys) {
			return new ModelKeyListAccessorImpl<T>(this.modelTypeName, this, keys);
		}

		@Override
		public ModelKeyListAccessor<T> createAccessorWithStringKeys(Collection<String> keys) {
			StringModelKeyConverter keyConverter = this.getStringKeyConverter();
			List<ModelKey> modelKeys = keyConverter.convert(keys);
			return this.createAccessor(modelKeys);
		}

		@Override
		public ModelKeyListAccessor<T> createAccessorWithModels(Collection<T> models) {
			return new LoadedModelKeyListAccessor<T>(this.modelTypeName, models);
		}

		// MARK: ObjectifyKeyConverter
		@Override
		public StringModelKeyConverter getStringKeyConverter() {
			return this.objectifyKeyConverter.getStringKeyConverter();
		}

		@Override
		public ModelKey readKey(Key<T> key) throws IllegalKeyConversionException, NullPointerException {
			return this.objectifyKeyConverter.readKey(key);
		}

		@Override
		public List<ModelKey> readKeys(Iterable<? extends Key<T>> keys) throws IllegalKeyConversionException {
			return this.objectifyKeyConverter.readKeys(keys);
		}

		@Override
		public Key<T> writeKey(ModelKey element) throws IllegalKeyConversionException {
			return this.objectifyKeyConverter.writeKey(element);
		}

		@Override
		public List<Key<T>> writeKeys(Iterable<? extends ModelKey> elements) throws IllegalKeyConversionException {
			return this.objectifyKeyConverter.writeKeys(elements);
		}

		@Override
		public List<ModelKey> convertTo(Collection<? extends Key<T>> input) throws ConversionFailureException {
			return this.objectifyKeyConverter.convertTo(input);
		}

		@Override
		public List<Key<T>> convertFrom(Collection<? extends ModelKey> input) throws ConversionFailureException {
			return this.objectifyKeyConverter.convertFrom(input);
		}

		@Override
		public ModelKeyType getModelKeyType() {
			return this.objectifyKeyConverter.getModelKeyType();
		}

		// MARK: ObjectifyQueryIterableFactory
		@Override
		public IndexedModelQueryIterable<T> makeIterable() {
			return this.iterableFactory.makeIterable();
		}

		@Override
		public IndexedModelQueryIterable<T> makeIterable(ResultsCursor cursor) {
			return this.iterableFactory.makeIterable(cursor);
		}

		@Override
		public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters) {
			return this.iterableFactory.makeIterable(parameters);
		}

		@Override
		public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters,
		                                                 ResultsCursor cursor) {
			return this.iterableFactory.makeIterable(parameters, cursor);
		}

		@Override
		public IndexedModelQueryIterable<T> makeIterable(Cursor cursor) {
			return this.iterableFactory.makeIterable(cursor);
		}

		@Override
		public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters,
		                                                 Cursor cursor) {
			return this.iterableFactory.makeIterable(parameters, cursor);
		}

		@Override
		public IndexedModelQueryIterable<T> makeIterable(SimpleQuery<T> query) {
			return this.iterableFactory.makeIterable(query);
		}

		@Override
		public IndexedModelQueryIterable<T> makeIterable(SimpleQuery<T> query,
		                                                 Cursor cursor) {
			return this.iterableFactory.makeIterable(query, cursor);
		}

		@Override
		public String toString() {
			return "ObjectifyDatabaseEntityImpl [type=" + this.type + ", modelTypeName=" + this.modelTypeName
			        + ", keyType=" + this.keyType + "]";
		}

		// MARK: Request Executor
		/**
		 * Internal implementation for executing a query.
		 *
		 * @author dereekb
		 *
		 */
		private class ObjectifyQueryRequestExecutor {

			private final ObjectifyQueryRequest<T> request;

			public ObjectifyQueryRequestExecutor(ObjectifyQueryRequest<T> request) {
				this.request = request;
			}

			public ObjectifyQueryModelResponse<T> query() {
				ObjectifyQueryRequestOptions options = this.request.getOptions();

				boolean cache = options.getAllowCache();
				Query<T> query = ObjectifyDatabaseEntityImpl.this.reader.makeQuery(cache);

				query = this.applyOptions(query);

				Query<T> filteredQuery = this.applyFilters(query);
				filteredQuery = this.applyResultsOrdering(filteredQuery);

				SimpleQuery<T> simpleQuery = this.applySimpleQueryFilters(filteredQuery);
				ObjectifyQueryModelResponse<T> response = new ObjectifyQueryModelResponseImpl(simpleQuery);
				return response;
			}

			private Query<T> applyOptions(Query<T> query) {
				ObjectifyQueryRequestOptions options = this.request.getOptions();

				Cursor cursor = options.getObjectifyQueryCursor();
				Integer offset = options.getOffset();
				Integer limit = options.getLimit();
				Integer chunk = options.getChunk();

				if (limit != null) {
					query = query.limit(limit);
				}

				if (offset != null) {
					query = query.offset(offset);
				}

				if (cursor != null) {
					query = query.startAt(cursor);
				}

				if (chunk != null) {
					query = query.chunk(chunk);
				}

				if (options.getAllowHybrid() == false) {
					query = query.hybrid(false);
				}

				return query;
			}

			private Query<T> applyFilters(Query<T> query) {
				Query<T> filteredQuery = query;

				for (ObjectifyQueryFilter filter : this.request.getQueryFilters()) {
					filteredQuery = filter.filter(filteredQuery);
				}

				return filteredQuery;
			}

			private SimpleQuery<T> applySimpleQueryFilters(Query<T> query) {
				SimpleQuery<T> filteredQuery = query;

				for (ObjectifySimpleQueryFilter<T> filter : this.request.getSimpleQueryFilters()) {
					filteredQuery = filter.filter(filteredQuery);
				}

				return filteredQuery;
			}

			protected Query<T> applyResultsOrdering(Query<T> query) {
				Iterable<ObjectifyQueryOrdering> resultsOrdering = this.request.getResultsOrdering();

				if (resultsOrdering != null) {
					for (ObjectifyQueryOrdering ordering : resultsOrdering) {
						String orderingString = ordering.getOrderingString();
						query = query.order(orderingString);
					}
				}

				return query;
			}

		}

		/**
		 * Internal {@link ObjectifyQueryModelResponse} implementation.
		 *
		 * @author dereekb
		 */
		private class ObjectifyQueryModelResponseImpl extends ObjectifyQueryKeyResponseImpl
		        implements ObjectifyQueryModelResponse<T> {

			private List<T> modelsResult;

			protected ObjectifyQueryModelResponseImpl(SimpleQuery<T> query) {
				super(query);
			}

			// MARK: ObjectifyQueryModelResponse
			@Override
			public List<T> queryModels() {
				if (this.modelsResult == null) {
					this.modelsResult = ObjectifyDatabaseEntityImpl.this
					        .getWithObjectifyKeys(this.queryObjectifyKeys());
				}

				return this.modelsResult;
			}

			@Override
			public QueryResultIterator<T> objectifyQueryModelsIterator() {
				return super.query.iterator();
			}

			@Override
			public IndexedModelQueryModelResultIterator<T> queryModelResultsIterator() {
				return new IndexedModelQueryModelResultIteratorImpl();
			}

			private class IndexedModelQueryModelResultIteratorImpl extends ObjectifyQueryModelResultIteratorImpl<T> {

				private IndexedModelQueryModelResultIteratorImpl() {
					super(objectifyQueryModelsIterator());
				}

			}

		}

		private class ObjectifyQueryKeyResponseImpl extends AbstractIndexedModelQueryKeyResponse
		        implements ObjectifyQueryKeyResponse<T> {

			protected final SimpleQuery<T> query;
			private final QueryKeys<T> keysQuery;

			private Result result;

			protected ObjectifyQueryKeyResponseImpl(SimpleQuery<T> query) {
				this.query = query;
				this.keysQuery = this.query.keys();
			}

			@Override
			public Integer getResultCount() {
				return this.query.count();
			}

			@Override
			public ResultsCursor getCursor() throws NoSearchCursorException {
				return ObjectifyCursor.make(this.getObjectifyCursor());
			}

			@Override
			public Cursor getObjectifyCursor() throws NoSearchCursorException {
				Cursor cursor = this.getResult().getCursor();

				if (cursor == null) {
					throw new NoSearchCursorException();
				}

				return cursor;
			}

			@Override
			public SimpleQuery<T> getQuery() {
				return this.query;
			}

			@Override
			public List<ModelKey> queryModelKeys() {
				List<Key<T>> objectifyKeys = this.queryObjectifyKeys();
				return ObjectifyDatabaseEntityImpl.this.objectifyKeyConverter.convertTo(objectifyKeys);
			}

			@Override
			public List<Key<T>> queryObjectifyKeys() {
				return this.getResult().getKeys();
			}

			@Override
			public QueryResultIterator<Key<T>> queryObjectifyKeyIterator() {
				return this.keysQuery.iterator();
			}

			// MARK: Internal
			private Result getResult() {
				if (this.result == null) {
					this.result = this.executeQuery();
				}

				return this.result;
			}

			private Result executeQuery() {
				QueryResultIterator<Key<T>> iterator = this.queryObjectifyKeyIterator();

				List<Key<T>> keys = IteratorUtility.iteratorToList(iterator);
				Cursor cursor = iterator.getCursor();

				return new Result(keys, cursor);
			}

			private class Result {

				private List<Key<T>> keys;
				private Cursor cursor;

				public Result(List<Key<T>> keys, Cursor cursor) {
					super();
					this.keys = keys;
					this.cursor = cursor;
				}

				public List<Key<T>> getKeys() {
					return this.keys;
				}

				public Cursor getCursor() {
					return this.cursor;
				}

			}

		}

	}

}
