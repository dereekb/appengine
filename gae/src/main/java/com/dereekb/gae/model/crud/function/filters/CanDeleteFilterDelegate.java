package com.dereekb.gae.model.crud.function.filters;

public interface CanDeleteFilterDelegate<T> {

	public boolean canDelete(T object);

}
