package com.dereekb.gae.server.datastore.objectify.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.googlecode.objectify.Key;

public class ObjectifyModelDatastoreComponent<T extends ObjectifyModel<T>> {

	protected final ObjectifyDatabase database;

	public ObjectifyModelDatastoreComponent(ObjectifyDatabase database, Class<T> type) {
		this.database = database;
		this.type = type;
	}

	protected final Class<T> type;

	public Class<T> getType() {
		return this.type;
	}

	// Make
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

	public List<Key<T>> makeKeysFromLongs(Collection<Long> ids) {
		return this.database.makeKeysFromLongs(this.type, ids);
	}

	public List<Key<T>> makeKeysFromNames(Collection<String> ids) {
		return this.database.makeKeysFromNames(this.type, ids);
	}

	public List<Key<T>> makeKeysFromModelKeys(Collection<ModelKey> keys) {
		return this.database.makeKeysFromModelKeys(this.type, keys);
	}

	public static <T> List<Long> getLongsFromKeys(Collection<Key<T>> keys) {

		List<Long> identifiers = new ArrayList<Long>();

		for (Key<T> key : keys) {
			Long identifier = key.getId();
			identifiers.add(identifier);
		}

		return identifiers;
	}

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

	public List<Key<T>> makeKeysFromObjectsList(Collection<T> objects) {

		List<Key<T>> keys = new ArrayList<Key<T>>();

		if (objects != null) {
			for (T object : objects) {
				keys.add(object.getObjectifyKey());
			}
		}

		return keys;
	}

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
