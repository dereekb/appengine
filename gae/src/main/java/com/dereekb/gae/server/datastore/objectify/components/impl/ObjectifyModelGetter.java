package com.dereekb.gae.server.datastore.objectify.components.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.googlecode.objectify.Key;

/**
 * Objectify implementation of {@link ObjectifyKeyedGetter}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyModelGetter<T extends ObjectifyModel<T>> extends ObjectifyModelDatastoreComponent<T>
        implements ObjectifyKeyedGetter<T> {

	public ObjectifyModelGetter(ObjectifyDatabase database, Class<T> type) {
		super(database, type);
	}

	@Override
    public T get(Key<T> key) {
		return this.database.get(key);
	}

	@Override
	public T get(T model) {
		Key<T> key = model.getObjectifyKey();
		T result = null;

		if (key != null) {
			result = this.get(key);
		}

		return result;
	}

	@Override
	public T get(ModelKey modelKey) {
		Key<T> key = this.database.makeKey(this.type, modelKey);

		if (key == null) {
			return null;
		}

		return this.database.get(key);
	}

	@Override
	public List<T> get(Iterable<T> models) {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		for (ObjectifyModel<T> model : models) {
			Key<T> key = model.getObjectifyKey();
			if (key != null) {
				keys.add(key);
			}
		}

		return this.database.getWithKeys(keys);
	}

	@Override
	public List<T> getWithKeys(Iterable<ModelKey> modelKeys) {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		for (ModelKey key : modelKeys) {
			Key<T> newKey = this.makeKey(key);
			if (newKey != null) {
				keys.add(newKey);
			}
		}

		return this.database.getWithKeys(keys);
	}

	@Override
	public List<T> getWithObjectifyKeys(Iterable<Key<T>> keys) {
		List<Key<T>> list = new ArrayList<Key<T>>();

		for (Key<T> key : keys) {
			list.add(key);
		}

		return this.database.getWithKeys(keys);
	}

	@Override
    public boolean exists(Key<T> key) {
		return this.database.exists(key);
	}

	@Override
	public boolean exists(ModelKey modelKey) throws IllegalArgumentException {
		Key<T> key = this.database.makeKey(this.type, modelKey);
		return this.database.exists(key);
	}

	@Override
	public Set<ModelKey> exists(Iterable<ModelKey> keys) {
		List<T> models = this.getWithKeys(keys);
		List<ModelKey> keyList = ModelKey.readModelKeys(models);
		Set<ModelKey> keysSet = new HashSet<ModelKey>(keyList);
		return keysSet;
	}

	@Override
	public boolean allExist(Iterable<ModelKey> keys) {
		Set<ModelKey> keysSet = new HashSet<ModelKey>();

		for (ModelKey key : keys) {
			keysSet.add(key);
		}

		List<T> models = this.getWithKeys(keysSet);
		boolean allExist = (keysSet.size() == models.size());
		return allExist;
	}

}
