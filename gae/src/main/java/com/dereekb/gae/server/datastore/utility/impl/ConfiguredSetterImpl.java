package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Basic wrapper that wraps a {@link Setter} instance and a boolean.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ConfiguredSetterImpl<T>
        implements ConfiguredSetter<T> {

	private Setter<T> setter;
	private boolean asynchronous = true;

	public ConfiguredSetterImpl(Setter<T> setter) {
		this.setter = setter;
	}

	public ConfiguredSetterImpl(Setter<T> setter, boolean asynchronous) {
		this.setter = setter;
		this.asynchronous = asynchronous;
	}

	public Setter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(Setter<T> setter) {
		this.setter = setter;
	}

	public boolean isAsynchronous() {
		return this.asynchronous;
	}

	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	// MARK: ConfiguredSetter
	@Override
	public final void save(T entity) {
		this.save(entity, this.asynchronous);
	}

	@Override
	public final void save(Iterable<T> entities) {
		this.save(entities, this.asynchronous);
	}

	@Override
	public final void delete(T entity) {
		this.delete(entity, this.asynchronous);
	}

	@Override
	public final void delete(Iterable<T> entities) {
		this.delete(entities, this.asynchronous);
	}

	// MARK: Setter
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
	public String toString() {
		return "ConfiguredSetterImpl [setter=" + this.setter + ", asynchronous=" + this.asynchronous + "]";
	}

}
