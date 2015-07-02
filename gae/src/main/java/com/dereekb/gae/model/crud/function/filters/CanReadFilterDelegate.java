package com.dereekb.gae.model.crud.function.filters;

public interface CanReadFilterDelegate<T> {

	public boolean canRead(T object);

}
