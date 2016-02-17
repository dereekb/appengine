package com.dereekb.gae.model.crud.deprecated.function.filters;

public interface CanCreateFilterDelegate<T> {

	public boolean canCreate(T object);

}
