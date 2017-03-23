package com.dereekb.gae.server.datastore.objectify.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.LoadedModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.ModelKeyListAccessorImpl;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistryFactory;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityModifier;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityReader;
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
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyKeyInSetFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestBuilderImpl;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterable;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.server.datastore.objectify.query.iterator.impl.ObjectifyQueryIterableFactoryImpl;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
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
	protected Objectify ofy() {
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
	        implements ObjectifyKeyConverter<T, ModelKey>, ObjectifyDatabaseEntity<T>,
	        ObjectifyDatabaseEntityReader<T> {

		private boolean configuredAsync = false;

		private final Class<T> type;
		private final String modelTypeName;
		private final ModelKeyType keyType;

		private final ObjectifyKeyConverter<T, ModelKey> objectifyKeyConverter;
		private final ObjectifyQueryRequestLimitedBuilderInitializer initializer;
		private final ObjectifyQueryIterableFactory<T> iterableFactory;

		protected ObjectifyDatabaseEntityImpl(Class<T> type) throws UnregisteredEntryTypeException {
			ObjectifyDatabaseEntityDefinition entity = ObjectifyDatabaseImpl.this.getDefinition(type);

			this.type = type;
			this.modelTypeName = entity.getEntityName();
			this.keyType = entity.getEntityKeyType();
			this.objectifyKeyConverter = ObjectifyModelKeyUtil.converterForType(type, this.keyType);
			this.initializer = entity.getQueryInitializer();
			this.iterableFactory = new ObjectifyQueryIterableFactoryImpl<T>(this);
		}

		public boolean isConfiguredAsync() {
			return this.configuredAsync;
		}

		public void setConfiguredAsync(boolean configuredAsync) {
			this.configuredAsync = configuredAsync;
		}

		public Class<T> getType() {
			return this.type;
		}

		public String getModelTypeName() {
			return this.modelTypeName;
		}

		public ModelKeyType getKeyType() {
			return this.keyType;
		}

		@Override
		public ObjectifyKeyConverter<T, ModelKey> getObjectifyKeyConverter() {
			return this.objectifyKeyConverter;
		}

		// MARK: ObjectifyDatabaseEntityReader
		@Override
		public ObjectifyDatabaseEntityReader<T> getReader() {
			return this;
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
		public boolean exists(Key<T> key) {
			return (this.get(key) != null);
		}

		@Override
		public Query<T> makeQuery(boolean allowCache) {
			Objectify objectify = ObjectifyDatabaseImpl.this.ofy().cache(allowCache);
			Query<T> query = objectify.load().type(this.type);
			return query;
		}

		// MARK: ObjectifyDatabaseEntityModifier
		@Override
		public ObjectifyDatabaseEntityModifier<T> getModifier(boolean async) {
			return new ObjectifyDatabaseEntityModifierImpl(async);
		}

		protected class ObjectifyDatabaseEntityModifierImpl
		        implements ObjectifyDatabaseEntityModifier<T> {

			private final boolean async;

			public ObjectifyDatabaseEntityModifierImpl(boolean async) {
				this.async = async;
			}

			@Override
			public boolean isAsyncronous() {
				return this.async;
			}

			// MARK: Put
			@Override
			public void put(T entity) {
				Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
				Result<Key<T>> result = objectify.save().entity(entity);

				if (this.async == false) {
					result.now();
				}
			}

			@Override
			public void put(Iterable<T> entities) {
				Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
				Result<Map<Key<T>, T>> result = objectify.save().entities(entities);

				if (this.async == false) {
					result.now();
				}
			}

			// MARK: Delete
			@Override
			public void delete(T entity) {
				if (entity != null) {
					Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
					Result<Void> result = objectify.delete().entity(entity);

					if (this.async == false) {
						result.now();
					}
				}
			}

			@Override
			public void delete(Key<T> key) {
				if (key != null) {
					Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
					Result<Void> result = objectify.delete().key(key);

					if (this.async == false) {
						result.now();
					}
				}
			}

			@Override
			public void delete(Ref<T> ref) {
				if (ref != null) {
					Key<T> key = ref.getKey();
					Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
					Result<Void> result = objectify.delete().key(key);

					if (this.async == false) {
						result.now();
					}
				}
			}

			@Override
			public void delete(Iterable<T> list) {
				if (list != null) {
					Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
					Result<Void> result = objectify.delete().entities(list);

					if (this.async == false) {
						result.now();
					}
				}
			}

			@Override
			public void deleteWithKeys(Iterable<Key<T>> list) {
				if (list != null) {
					Objectify objectify = ObjectifyDatabaseImpl.this.ofy();
					Result<Void> result = objectify.delete().keys(list);

					if (this.async == false) {
						result.now();
					}
				}
			}

		}

		@Override
		public List<T> getWithObjectifyKeys(Iterable<Key<T>> keys) {
			return this.keysGet(keys);
		}

		@Override
		public boolean exists(T model) {
			return this.exists(model.getObjectifyKey());
		}

		@Override
		public boolean exists(ModelKey key) {
			Key<T> objectifyKey = this.objectifyKeyConverter.writeKey(key);
			return this.exists(objectifyKey);
		}

		@Override
		public boolean allExist(Iterable<ModelKey> keys) {
			Set<ModelKey> modelKeys = IteratorUtility.iterableToSet(keys);
			List<T> models = this.getWithKeys(modelKeys);
			return (modelKeys.size() == models.size());
		}

		@Override
		public Set<ModelKey> exists(Iterable<ModelKey> keys) {
			List<T> models = this.getWithKeys(keys);
			List<ModelKey> keyList = ModelKey.readModelKeys(models);
			return new HashSet<ModelKey>(keyList);
		}

		@Override
		public T get(ModelKey key) {
			Key<T> objectifyKey = this.objectifyKeyConverter.writeKey(key);
			return this.get(objectifyKey);
		}

		@Override
		public T get(T model) {
			T result = null;
			Key<T> key = model.getObjectifyKey();

			if (key != null) {
				result = this.get(key);
			}

			return result;
		}

		@Override
		public List<T> get(Iterable<T> models) {
			List<Key<T>> keys = ObjectifyUtility.readKeys(models);
			return this.keysGet(keys);
		}

		@Override
		public List<T> getWithKeys(Iterable<ModelKey> keys) {
			List<ModelKey> modelKeys = IteratorUtility.iterableToList(keys);
			List<Key<T>> objectifyKeys = this.objectifyKeyConverter.convertFrom(modelKeys);
			return this.keysGet(objectifyKeys);
		}

		@Override
		public void delete(Key<T> key,
		                   boolean async) {
			this.getModifier(async).delete(key);
		}

		@Override
		public void deleteWithObjectifyKeys(Iterable<Key<T>> keys,
		                                    boolean async) {
			this.getModifier(async).deleteWithKeys(keys);
		}

		@Override
		public void save(T entity,
		                 boolean async) {
			this.getModifier(async).put(entity);
		}

		@Override
		public void save(Iterable<T> entities,
		                 boolean async) {
			this.getModifier(async).put(entities);
		}

		@Override
		public void save(T entity) {
			this.save(entity, this.isConfiguredAsync());
		}

		@Override
		public void save(Iterable<T> entities) {
			this.save(entities, this.isConfiguredAsync());
		}

		@Override
		public void delete(T entity) {
			this.delete(entity, this.isConfiguredAsync());
		}

		@Override
		public void delete(Iterable<T> entities) {
			this.delete(entities, this.isConfiguredAsync());
		}

		@Override
		public void deleteWithKey(ModelKey key) {
			this.deleteWithKey(key, this.isConfiguredAsync());
		}

		@Override
		public void deleteWithKeys(Iterable<ModelKey> keys) {
			this.deleteWithKeys(keys, this.isConfiguredAsync());
		}

		@Override
		public void delete(T entity,
		                   boolean async) {
			this.getModifier(async).delete(entity);
		}

		@Override
		public void delete(Iterable<T> entities,
		                   boolean async) {
			this.getModifier(async).delete(entities);
		}

		@Override
		public void deleteWithKey(ModelKey key,
		                          boolean async) {
			Key<T> objectifyKey = this.objectifyKeyConverter.writeKey(key);
			this.getModifier(async).delete(objectifyKey);
		}

		@Override
		public void deleteWithKeys(Iterable<ModelKey> keys,
		                           boolean async) {
			List<ModelKey> modelKeys = IteratorUtility.iterableToList(keys);
			List<Key<T>> objectifyKeys = this.objectifyKeyConverter.convertFrom(modelKeys);
			this.getModifier(async).deleteWithKeys(objectifyKeys);
		}

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
		public ModelKeyListAccessor<T> createAccessorWithModels(Collection<T> models) {
			return new LoadedModelKeyListAccessor<T>(this.modelTypeName, models);
		}

		// MARK: ObjectifyKeyConverter
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
		public ObjectifyQueryIterable<T> makeIterable() {
			return this.iterableFactory.makeIterable();
		}

		@Override
		public ObjectifyQueryIterable<T> makeIterable(Cursor cursor) {
			return this.iterableFactory.makeIterable(cursor);
		}

		@Override
		public ObjectifyQueryIterable<T> makeIterable(Map<String, String> parameters,
		                                              Cursor cursor) {
			return this.iterableFactory.makeIterable(parameters, cursor);
		}

		@Override
		public ObjectifyQueryIterable<T> makeIterable(SimpleQuery<T> query) {
			return this.iterableFactory.makeIterable(query);
		}

		@Override
		public ObjectifyQueryIterable<T> makeIterable(SimpleQuery<T> query,
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
				Query<T> query = ObjectifyDatabaseEntityImpl.this.makeQuery(cache);

				query = this.applyOptions(query);

				Query<T> filteredQuery = this.applyFilters(query);
				filteredQuery = this.applyResultsOrdering(filteredQuery);

				SimpleQuery<T> simpleQuery = this.applySimpleQueryFilters(filteredQuery);
				ObjectifyQueryModelResponse<T> response = new ObjectifyQueryModelResponseImpl(simpleQuery);
				return response;
			}

			private Query<T> applyOptions(Query<T> query) {
				ObjectifyQueryRequestOptions options = this.request.getOptions();

				Cursor cursor = options.getQueryCursor();
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
			public QueryResultIterator<T> queryModelsIterator() {
				return this.queryModelsIterator();
			}

		}

		private class ObjectifyQueryKeyResponseImpl
		        implements ObjectifyQueryKeyResponse<T> {

			protected final SimpleQuery<T> query;
			private final QueryKeys<T> keysQuery;

			private Result result;

			protected ObjectifyQueryKeyResponseImpl(SimpleQuery<T> query) {
				this.query = query;
				this.keysQuery = this.query.keys();
			}

			@Override
			public boolean hasResults() {
				return this.getResultCount() > 0;
			}

			@Override
			public Integer getResultCount() {
				return this.query.count();
			}

			@Override
			public Cursor getCursor() throws NoSearchCursorException {
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
