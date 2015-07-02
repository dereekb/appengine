package com.dereekb.gae.model.crud.function.filters;


public interface CanUpdateFilterDelegate<T> {

	public boolean canUpdate(T object);
	
}
