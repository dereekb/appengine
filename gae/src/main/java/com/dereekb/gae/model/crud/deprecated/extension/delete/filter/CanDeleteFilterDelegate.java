package com.dereekb.gae.model.crud.deprecated.extension.delete.filter;

/**
 * Delegate for {@link CanDeleteFilter}.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public interface CanDeleteFilterDelegate<T> {

	public boolean canDelete(T object);

}
