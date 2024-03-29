package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Basic wrapper that wraps a {@link Setter} instance and whether or not to save
 * asynchronously or not.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public class ConfiguredSetterImpl<T>
        implements ConfiguredSetter<T> {

	public static final Boolean DEFAULT_ASYNC_STATE = true;

	private Setter<T> setter;
	private Boolean asynchronous;

	public ConfiguredSetterImpl(Setter<T> setter) {
		this(setter, DEFAULT_ASYNC_STATE);
	}

	public ConfiguredSetterImpl(Setter<T> setter, Boolean asynchronous) throws IllegalArgumentException {
		this.setSetter(setter);
		this.setAsynchronous(asynchronous);
	}

	public Setter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(Setter<T> setter) throws IllegalArgumentException {
		if (setter == null) {
			throw new IllegalArgumentException("setter cannot be null.");
		}

		this.setter = setter;
	}

	public Boolean isAsynchronous() {
		return this.asynchronous;
	}

	public void setAsynchronous(Boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	// MARK: ConfiguredSetter
	@Override
	public final void delete(T entity) {
		this.delete(entity);
	}

	@Override
	public final void delete(Iterable<T> entities) {
		this.delete(entities);
	}

	// MARK: Setter
	@Override
	public void update(T entity) throws UpdateUnkeyedEntityException {
		this.setter.update(entity);
	}

	@Override
	public void update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.setter.update(entities);
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
	public void deleteAsync(T entity) {
		this.setter.deleteAsync(entity);
	}

	@Override
	public void deleteAsync(Iterable<T> entities) {
		this.setter.deleteAsync(entities);
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
	public String toString() {
		return "ConfiguredSetterImpl [setter=" + this.setter + ", asynchronous=" + this.asynchronous + "]";
	}

	@Override
	public void updateAsync(T entity) throws UpdateUnkeyedEntityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteWithKeyAsync(ModelKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteWithKeysAsync(Iterable<ModelKey> keys) {
		// TODO Auto-generated method stub

	}

}
