package com.dereekb.gae.model.crud.function.filters;

public interface CanCreateFilterDelegate<T> {

	public boolean canCreate(T object);

}
