package com.dereekb.gae.server.datastore.objectify.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModelRegistry;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistryFactory;
import com.dereekb.gae.server.datastore.objectify.core.exception.UnregisteredEntryTypeException;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyWriter;
import com.dereekb.gae.server.datastore.objectify.keys.impl.ObjectifyLongKeysConverter;
import com.dereekb.gae.server.datastore.objectify.keys.impl.ObjectifyStringKeysConverter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.QueryKeys;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * Objectify Database Singleton used for performing changes using Objectify.
 *
 * @author dereekb
 *
 */
public class ObjectifyDatabase
        implements ObjectifyRegistryFactory {

	private Map<String, Class<?>> aliases = new HashMap<>();
	private Map<Class<?>, ObjectifyDatabaseEntityDefinition<?>> definitions = new HashMap<>();

	public ObjectifyDatabase(Iterable<ObjectifyDatabaseEntityDefinition<?>> entities) {
		super();
		this.addEntityDefinitions(entities);
	}

	private void addEntityDefinitions(Iterable<ObjectifyDatabaseEntityDefinition<?>> entities) {
		ObjectifyFactory factory = ObjectifyService.factory();

		for (ObjectifyDatabaseEntityDefinition<?> entity : entities) {
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
		ObjectifyDatabaseEntityDefinition<?> entity = this.definitions.get(type);

		if (entity == null) {
			throw new UnregisteredEntryTypeException(type);
		}

		ModelKeyType keyType = entity.getEntityKeyType();
		ObjectifyKeyWriter<T, ModelKey> keyWriter = this.makeKeyWriter(type, keyType);
		ObjectifyModelRegistry<T> registry = new ObjectifyModelRegistry<T>(this, type, keyWriter);
		return registry;
	}

	private <T> ObjectifyKeyWriter<T, ModelKey> makeKeyWriter(Class<T> type,
	                                                          ModelKeyType keyType) {
		ObjectifyKeyWriter<T, ModelKey> writer = null;

		switch (keyType) {
			case NAME:
				writer = new ObjectifyStringKeysConverter<T>(type);
				break;
			case NUMBER:
				writer = new ObjectifyLongKeysConverter<T>(type);
				break;
			case DEFAULT:
			default:
				throw new IllegalArgumentException("KeyType cannot be 'Default'.");
		}

		return writer;
	}

	// Objectify
	private Objectify ofy() {
		return ObjectifyService.ofy();
	}

	// Make
	public <T> Ref<T> makeRef(Key<T> key) {
		return Ref.create(key);
	}

	public <T> Ref<T> makeRef(T value) {
		return Ref.create(value);
	}

	public <T> Key<T> makeKey(Class<T> type,
	                          Long id) throws IllegalArgumentException {
		return Key.create(type, id);
	}

	public <T> Key<T> makeKey(Class<T> type,
	                          String name) throws IllegalArgumentException {
		return Key.create(type, name);
	}

	public <T> Key<T> makeKey(Class<T> type,
	                          ModelKey modelKey) throws IllegalArgumentException {
		Key<T> key;

		if (modelKey.getType() == ModelKeyType.NAME) {
			key = this.makeKey(type, modelKey.getName());
		} else {
			key = this.makeKey(type, modelKey.getId());
		}

		return key;
	}

	/**
	 * Creates Objectify {@link Key}s from the input names.
	 *
	 * Does not check/filter repeat values.
	 *
	 * @param type
	 *            Class type that the key references.
	 * @param ids
	 *            Identifiers/Names to use
	 * @return List of keys of the input type.
	 * @throws IllegalArgumentException
	 *             Thrown if an invalid name is input.
	 */
	public <T> List<Key<T>> makeKeysFromNames(Class<T> type,
	                                          Iterable<String> ids) throws IllegalArgumentException {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		for (String name : ids) {
			Key<T> newKey = Key.create(type, name);
			keys.add(newKey);
		}

		return keys;
	}

	/**
	 * Creates a list of Objectify {@link Key} values from the input long
	 * identifiers.
	 *
	 * Does not check/filter repeat values.
	 *
	 * @param type
	 *            Class type that the key references.
	 * @param ids
	 *            Identifiers to use.
	 * @return List of keys of the input type that correspond with the input
	 *         values.
	 * @throws IllegalArgumentException
	 *             Thrown if an invalid identifier is input. Zero is an invalid
	 *             identifier.
	 */
	public <T> List<Key<T>> makeKeysFromLongs(Class<T> type,
	                                          Iterable<Long> ids) throws IllegalArgumentException {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		if (ids != null) {
			for (Long id : ids) {
				Key<T> newKey = this.makeKey(type, id);
				keys.add(newKey);
			}
		}

		return keys;
	}

	/**
	 * Creates a list of Objectify {@link Key} values from the input
	 * {@link ModelKey} keys.
	 *
	 * Does not check/filter repeat values.
	 *
	 * @param type
	 *            Class type that the key references.
	 * @param ids
	 *            Identifiers to use.
	 * @return List of keys of the input type that correspond with the input
	 *         values.
	 * @throws IllegalArgumentException
	 *             Thrown if an invalid identifier is input. Zero is an invalid
	 *             identifier.
	 */
	public <T> List<Key<T>> makeKeysFromModelKeys(Class<T> type,
	                                              Collection<ModelKey> modelKeys) throws IllegalArgumentException {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		if (modelKeys != null) {
			for (ModelKey modelKey : modelKeys) {
				Key<T> newKey = this.makeKey(type, modelKey);
				keys.add(newKey);
			}
		}

		return keys;
	}

	// Get
	public <T> T get(Class<T> type,
	                 Long id) {
		Key<T> key = Key.create(type, id);
		return this.ofy().load().key(key).now();
	}

	public <T> T get(Class<T> type,
	                 String name) {
		Key<T> key = Key.create(type, name);
		return this.ofy().load().key(key).now();
	}

	public <T> T get(Key<T> key) {
		return this.ofy().load().key(key).now();
	}

	public <T> List<T> get(Iterable<Key<T>> list) {
		Map<Key<T>, T> results = this.ofy().load().keys(list);
		List<T> values = new ArrayList<T>(results.values());
		return values;
	}

	public <T> List<T> getWithRefs(Iterable<Ref<T>> list) {
		Map<Key<T>, T> results = this.ofy().load().refs(list);
		List<T> values = new ArrayList<T>(results.values());
		return values;
	}

	public <T> List<T> getWithKeys(Iterable<Key<T>> list) {
		Map<Key<T>, T> results = this.ofy().load().keys(list);
		List<T> values = new ArrayList<T>(results.values());
		return values;
	}

	// Add/Put

	public <T> void put(T entity,
	                    boolean async) {
		Result<Key<T>> result = this.ofy().save().entity(entity);

		if (async == false) {
			result.now();
		}
	}

	public <T> void put(Iterable<T> entities,
	                    boolean async) {
		Result<Map<Key<T>, T>> result = this.ofy().save().entities(entities);

		if (async == false) {
			result.now();
		}
	}

	// Delete
	public <T> void delete(T entity,
	                       boolean async) {
		if (entity != null) {
			Result<Void> result = this.ofy().delete().entity(entity);

			if (async == false) {
				result.now();
			}
		}
	}

	public <T> void delete(Key<T> key,
	                       boolean async) {
		if (key != null) {
			Result<Void> result = this.ofy().delete().key(key);

			if (async == false) {
				result.now();
			}
		}
	}

	public <T> void delete(Ref<T> ref,
	                       boolean async) {
		if (ref != null) {
			Key<T> key = ref.getKey();
			Result<Void> result = this.ofy().delete().key(key);

			if (async == false) {
				result.now();
			}
		}
	}

	public <T> void delete(Iterable<T> list,
	                       boolean async) {
		if (list != null) {
			Result<Void> result = this.ofy().delete().entities(list);

			if (async == false) {
				result.now();
			}
		}
	}

	public <T> void deleteWithKeys(Iterable<Key<T>> list,
	                               boolean async) {
		if (list != null) {
			Result<Void> result = this.ofy().delete().keys(list);

			if (async == false) {
				result.now();
			}
		}
	}

	// Exists
	public boolean exists(Key<?> key) {
		boolean exists = false;

		try {
			this.ofy().load().key(key).safe();
			exists = true;
		} catch (NotFoundException e) {
			exists = false;
		}

		return exists;
	}

	// Query

	public <T> SimpleQuery<T> query(ObjectifyQuery<T> query) {
		Objectify objectify = this.ofy();
		SimpleQuery<T> finishedQuery = query.executeQuery(objectify);
		return finishedQuery;
	}

	public <T> List<Key<T>> queryForKeys(ObjectifyQuery<T> query) {
		SimpleQuery<T> finishedQuery = this.query(query);
		QueryKeys<T> queryKeys = finishedQuery.keys();

		List<Key<T>> keys = queryKeys.list();
		return keys;
	}

	public <T> List<T> queryForEntities(ObjectifyQuery<T> query) {
		SimpleQuery<T> finishedQuery = this.query(query);
		List<T> entities = finishedQuery.list();
		return entities;
	}

	public <T> QueryResultIterator<T> queryForIterable(ObjectifyQuery<T> query) {
		Objectify objectify = this.ofy();
		SimpleQuery<T> finishedQuery = query.executeQuery(objectify);
		QueryResultIterable<T> iterable = finishedQuery.iterable();
		return iterable.iterator();
	}

}
