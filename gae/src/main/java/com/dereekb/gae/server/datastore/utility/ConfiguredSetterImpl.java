package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.Setter;

/**
 * Basic wrapper that wraps a {@link Setter} instance and a boolean.
 *
 * @author dereekb
 *
 * @param <T>
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
	public void save(T entity) {
		this.setter.save(entity, this.asynchronous);
	}

	@Override
	public void save(Iterable<T> entities) {
		this.setter.save(entities, this.asynchronous);
	}

	@Override
	public void delete(T entity) {
		this.setter.delete(entity, this.asynchronous);
	}

	@Override
	public void delete(Iterable<T> entities) {
		this.setter.delete(entities, this.asynchronous);
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

}
