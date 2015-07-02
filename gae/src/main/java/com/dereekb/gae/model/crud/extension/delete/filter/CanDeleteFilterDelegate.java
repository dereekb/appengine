package com.dereekb.gae.model.crud.extension.delete.filter;

/**
 * Delegate for {@link CanDeleteFilter}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface CanDeleteFilterDelegate<T> {

	public boolean canDelete(T object);

}
