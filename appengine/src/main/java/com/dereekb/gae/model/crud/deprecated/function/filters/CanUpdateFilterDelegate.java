package com.dereekb.gae.model.crud.deprecated.function.filters;


public interface CanUpdateFilterDelegate<T> {

	public boolean canUpdate(T object);
	
}
