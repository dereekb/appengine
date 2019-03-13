package com.dereekb.gae.server.datastore.objectify.components.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.googlecode.objectify.Key;

/**
 * Abstract component used by other Objectify components.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public abstract class ObjectifyModelDatastoreComponent<T extends ObjectifyModel<T>> {

	protected final ObjectifyDatabaseImpl database;

	public ObjectifyModelDatastoreComponent(ObjectifyDatabaseImpl database, Class<T> type) {
		this.database = database;
		this.type = type;
	}

	protected final Class<T> type;

	public Class<T> getType() {
		return this.type;
	}

	// Make
	@Deprecated
	public Key<T> makeKey(Long id) throws IllegalArgumentException {
		if (id != null) {
			return this.database.makeKey(this.type, id);
		} else {
			return null;
		}
	}

	public Key<T> makeKey(ModelKey key) throws IllegalArgumentException {
		if (key != null) {
			return this.database.makeKey(this.type, key);
		} else {
			return null;
		}
	}

	@Deprecated
	public List<Key<T>> makeKeysFromLongs(Collection<Long> ids) {
		return this.database.makeKeysFromLongs(this.type, ids);
	}

	@Deprecated
	public List<Key<T>> makeKeysFromNames(Collection<String> ids) {
		return this.database.makeKeysFromNames(this.type, ids);
	}

	@Deprecated
	public List<Key<T>> makeKeysFromModelKeys(Collection<ModelKey> keys) {
		return this.database.makeKeysFromModelKeys(this.type, keys);
	}

	@Deprecated
	public static <T> List<Long> getLongsFromKeys(Collection<Key<T>> keys) {
		List<Long> identifiers = new ArrayList<Long>();

		for (Key<T> key : keys) {
			Long identifier = key.getId();
			identifiers.add(identifier);
		}

		return identifiers;
	}

	@Deprecated
	public static <T> List<String> getNamesFromKeys(Collection<Key<T>> keys) {
		List<String> names = new ArrayList<String>();

		for (Key<T> key : keys) {
			String name = key.getName();
			names.add(name);
		}

		return names;
	}

	/**
	 * Use when the key type is unknown.
	 *
	 * @param ids
	 * @return
	 */
	@Deprecated
	public List<Key<T>> makeKeysFromStringIds(Collection<String> ids) {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		if (ids != null) {
			List<Long> longIds = new ArrayList<Long>();

			for (String id : ids) {
				Long longId = new Long(id);
				longIds.add(longId);
			}

			keys = this.database.makeKeysFromLongs(this.type, longIds);
		}

		return keys;
	}

	@Deprecated
	public List<Key<T>> makeKeysFromObjectsList(Collection<T> objects) {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		if (objects != null) {
			for (T object : objects) {
				keys.add(object.getObjectifyKey());
			}
		}

		return keys;
	}

	@Deprecated
	public Key<T> makeKey(String name) {
		if (name != null) {
			return this.database.makeKey(this.type, name);
		} else {
			return null;
		}
	}

	/**
	 * Returns a list of Longs representing ids from the given list of keys.
	 *
	 * @param keys
	 * @return
	 */
	@Deprecated
	public List<Long> makeLongListFromKeys(Collection<Key<T>> keys) {

		List<Long> ids = new ArrayList<Long>();

		if ((keys != null) && (keys.isEmpty() == false)) {
			for (Key<T> key : keys) {

				if (key == null) {
					continue;
				}

				ids.add(key.getId());
			}
		}

		return ids; // Returns a List always.
	}

}