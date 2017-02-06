package com.dereekb.gae.server.datastore.objectify;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.LoadedModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.ModelKeyListAccessorImpl;
import com.dereekb.gae.server.datastore.objectify.components.impl.ObjectifyModelGetter;
import com.dereekb.gae.server.datastore.objectify.components.impl.ObjectifyModelSetter;
import com.dereekb.gae.server.datastore.objectify.components.query.impl.ObjectifyQueryServiceImpl;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyWriter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryResponse;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.server.datastore.utility.ConfiguredDeleter;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.googlecode.objectify.Key;

/**
 * {@link ObjectifyRegistry} implementation for {@link ObjectifyModel}
 * instances.
 * <p>
 * {@link ConfiguredSetter} implementation that saves/deletes asynchronously.
 * <p>
 * {@link ConfiguredDeleter} implementation that deletes asynchronously.
 *
 * @author dereekb
 *
 * @deprecated Replaced by {@link ObjectifyDatabaseImpl}.
 *
 * @param <T>
 *            Model type
 */
@Deprecated
public class ObjectifyModelRegistry<T extends ObjectifyModel<T>>
        implements ObjectifyRegistry<T>, ConfiguredSetter<T>, ConfiguredDeleter {

	protected String modelType;
	protected final Class<T> type;
	protected final ObjectifyQueryServiceImpl<T> query;
	protected final ObjectifyModelGetter<T> getter;
	protected final ObjectifyModelSetter<T> setter;
	protected final ObjectifyKeyWriter<T, ModelKey> keyWriter;

	public ObjectifyModelRegistry(ObjectifyDatabaseImpl database, Class<T> type, ObjectifyKeyWriter<T, ModelKey> keyWriter) {
		this(null, database, type, keyWriter);
	}

	public ObjectifyModelRegistry(String modelType,
	        ObjectifyDatabaseImpl database,
	        Class<T> type,
	        ObjectifyKeyWriter<T, ModelKey> keyWriter) throws IllegalArgumentException {

		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}

		if (keyWriter == null) {
			throw new IllegalArgumentException("Key Writer cannot be null.");
		}

		this.type = type;
		this.keyWriter = keyWriter;

		this.setModelType(modelType);

		this.query = new ObjectifyQueryServiceImpl<T>(database, type);
		this.getter = new ObjectifyModelGetter<T>(database, type);
		this.setter = new ObjectifyModelSetter<T>(database, type);
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			modelType = this.type.getSimpleName();
		}

		this.modelType = modelType;
	}

	@Override
	public List<T> get(Iterable<T> models) {
		return this.getter.get(models);
	}

	@Override
	public boolean exists(ModelKey key) {
		return this.getter.exists(key);
	}

	@Override
	public boolean allExist(Iterable<ModelKey> keys) {
		return this.getter.allExist(keys);
	}

	@Override
	public Set<ModelKey> exists(Iterable<ModelKey> keys) {
		return this.exists(keys);
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
	public T get(ModelKey key) {
		return this.getter.get(key);
	}

	@Override
	public T get(T model) {
		return this.getter.get(model);
	}

	@Override
	public List<T> getWithKeys(Iterable<ModelKey> keys) {
		return this.getter.getWithKeys(keys);
	}

	@Override
	public List<T> getWithObjectifyKeys(Iterable<Key<T>> keys) {
		return this.getter.getWithObjectifyKeys(keys);
	}

	// MARK: ConfiguredSetter
	@Override
	public void save(T entity) {
		this.save(entity, true);
	}

	@Override
	public void save(Iterable<T> entities) {
		this.save(entities, true);
	}

	@Override
	public void delete(T entity) {
		this.delete(entity, true);
	}

	@Override
	public void delete(Iterable<T> entities) {
		this.delete(entities, true);
	}

	@Override
	public void save(T entity,
	                 boolean async) {
		this.setter.save(entity, async);
	}

	@Override
	public void save(Iterable<T> entities,
	                 boolean async) {
		this.setter.save(entities, async);
	}

	@Override
	public void delete(T entity,
	                   boolean async) {
		this.setter.delete(entity, async);
	}

	@Override
	public void delete(Iterable<T> entities,
	                   boolean async) {
		this.setter.delete(entities, async);
	}

	@Override
	public void delete(Key<T> entity,
	                   boolean async) {
		this.setter.delete(entity, async);
	}

	@Override
	public void deleteWithKey(ModelKey key) {
		this.deleteWithKey(key, true);
	}

	// MARK: ConfiguredDeleter
	@Override
	public void deleteWithKeys(Iterable<ModelKey> keys) {
		this.deleteWithKeys(keys, true);
	}

	@Override
	public void deleteWithKey(ModelKey modelKey, boolean async) {
		Key<T> key = this.keyWriter.writeKey(modelKey);
		this.delete(key, async);
	}

	@Override
	public void deleteWithKeys(Iterable<ModelKey> modelKeys,
	                           boolean async) {
		List<Key<T>> keys = this.keyWriter.writeKeys(modelKeys);
		this.deleteWithObjectifyKeys(keys, async);
	}

	@Override
	public void deleteWithObjectifyKeys(Iterable<Key<T>> entities,
	                           boolean async) {
		this.setter.deleteWithObjectifyKeys(entities, async);
	}


	@Override
	public ModelKeyListAccessor<T> createAccessor() {
		return new ModelKeyListAccessorImpl<T>(this.modelType, this);
	}

	@Override
	public ModelKeyListAccessor<T> createAccessor(Collection<ModelKey> keys) {
		return new ModelKeyListAccessorImpl<T>(this.modelType, this, keys);
	}

	@Override
	public ModelKeyListAccessor<T> createAccessorWithModels(Collection<T> models) {
		return new LoadedModelKeyListAccessor<T>(this.modelType, models);
	}

	// MARK: Query
	@Override
	public Boolean modelsExist(Collection<Key<T>> keys) {
		return this.query.modelsExist(keys);
	}

	@Override
	public Set<Key<T>> filterExistingModels(Collection<Key<T>> keys) {
		return this.query.filterExistingModels(keys);
	}

	@Override
	public ObjectifyQueryIterableFactory<T> makeIterableQueryFactory() {
		return this.query.makeIterableQueryFactory();
	}

	@Override
	public ObjectifyQueryRequestBuilder<T> makeQuery() {
		return this.query.makeQuery();
	}

	@Override
	public ObjectifyQueryRequestBuilder<T> makeQuery(Map<String, String> parameters) {
		return this.query.makeQuery(parameters);
	}

	@Override
	public ObjectifyQueryResponse<T> query(ObjectifyQueryRequest<T> request) {
		return this.query.query(request);
	}

}
