package com.dereekb.gae.server.datastore.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;
import com.dereekb.gae.server.datastore.exception.UninitializedModelException;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link GetterSetter} implementation.
 *
 * @author dereekb
 *
 */
public class GetterSetterImpl<T extends UniqueModel>
        implements GetterSetter<T> {

	private Getter<T> getter;
	private Setter<T> setter;

	public GetterSetterImpl(GetterSetter<T> getterSetter) {
		this(getterSetter, getterSetter);
	}

	public GetterSetterImpl(Getter<T> getter, Setter<T> setter) {
		super();
		this.setGetter(getter);
		this.setSetter(setter);
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	public Setter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(Setter<T> setter) {
		if (setter == null) {
			throw new IllegalArgumentException("setter cannot be null.");
		}

		this.setter = setter;
	}

	// MARK: GetterSetterDeleter
	@Override
	public String getModelType() {
		return this.getter.getModelType();
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

	@Override
	public void store(T entity) throws StoreKeyedEntityException {
		this.setter.store(entity);
	}

	@Override
	public void store(Iterable<T> entities) throws StoreKeyedEntityException {
		this.setter.store(entities);
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
	public void delete(T entity) {
		this.setter.delete(entity);
	}

	@Override
	public void delete(Iterable<T> entities) {
		this.setter.delete(entities);
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
	public void deleteWithKeyAsync(ModelKey key) {
		this.setter.deleteWithKeyAsync(key);
	}

	@Override
	public void deleteWithKeysAsync(Iterable<ModelKey> keys) {
		this.setter.deleteWithKeysAsync(keys);
	}

	@Override
	public String toString() {
		return "GetterSetterImpl [getter=" + this.getter + ", setter=" + this.setter + "]";
	}

}
