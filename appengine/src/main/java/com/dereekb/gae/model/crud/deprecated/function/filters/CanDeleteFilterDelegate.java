package com.dereekb.gae.model.crud.deprecated.function.filters;

public interface CanDeleteFilterDelegate<T> {

	public boolean canDelete(T object);

}
