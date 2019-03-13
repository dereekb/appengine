package com.dereekb.gae.model.crud.deprecated.function.filters;

public interface CanReadFilterDelegate<T> {

	public boolean canRead(T object);

}
