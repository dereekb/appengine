package com.dereekb.gae.server.datastore.impl;

import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.Saver;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Setter} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SetterImpl<T extends UniqueModel>
        implements Setter<T> {

	private Storer<T> storer;
	private Updater<T> updater;
	private Deleter<T> deleter;

	public SetterImpl(Saver<T> saver, Deleter<T> deleter) {
		this(saver, saver, deleter);
	}

	public SetterImpl(Storer<T> storer, Updater<T> updater, Deleter<T> deleter) {
		super();
		this.setStorer(storer);
		this.setUpdater(updater);
		this.setDeleter(deleter);
	}

	public Storer<T> getStorer() {
		return this.storer;
	}

	public void setStorer(Storer<T> storer) {
		if (storer == null) {
			throw new IllegalArgumentException("storer cannot be null.");
		}

		this.storer = storer;
	}

	public Updater<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}

		this.updater = updater;
	}

	public Deleter<T> getDeleter() {
		return this.deleter;
	}

	public void setDeleter(Deleter<T> deleter) {
		if (deleter == null) {
			throw new IllegalArgumentException("deleter cannot be null.");
		}

		this.deleter = deleter;
	}

	// MARK: Setter
	@Override
	public void store(T entity) throws StoreKeyedEntityException {
		this.storer.store(entity);
	}

	@Override
	public void store(Iterable<T> entities) throws StoreKeyedEntityException {
		this.storer.store(entities);
	}

	@Override
	public void update(T entity) throws UpdateUnkeyedEntityException {
		this.updater.update(entity);
	}

	@Override
	public void update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.updater.update(entities);
	}

	@Override
	public void updateAsync(T entity) throws UpdateUnkeyedEntityException {
		this.updater.updateAsync(entity);
	}

	@Override
	public void updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.updater.update(entities);
	}

	@Override
	public void delete(T entity) {
		this.deleter.delete(entity);
	}

	@Override
	public void delete(Iterable<T> entities) {
		this.deleter.delete(entities);
	}

	@Override
	public void deleteAsync(T entity) {
		this.deleter.deleteAsync(entity);
	}

	@Override
	public void deleteAsync(Iterable<T> entities) {
		this.deleter.deleteAsync(entities);
	}

	@Override
	public void deleteWithKey(ModelKey key) {
		this.deleter.deleteWithKey(key);
	}

	@Override
	public void deleteWithKeys(Iterable<ModelKey> keys) {
		this.deleter.deleteWithKeys(keys);
	}

	@Override
	public void deleteWithKeyAsync(ModelKey key) {
		this.deleter.deleteWithKeyAsync(key);
	}

	@Override
	public void deleteWithKeysAsync(Iterable<ModelKey> keys) {
		this.deleter.deleteWithKeysAsync(keys);
	}

	@Override
	public String toString() {
		return "SetterImpl [storer=" + this.storer + ", updater=" + this.updater + ", deleter=" + this.deleter + "]";
	}

}
