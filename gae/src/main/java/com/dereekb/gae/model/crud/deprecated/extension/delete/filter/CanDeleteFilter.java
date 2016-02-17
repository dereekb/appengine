package com.dereekb.gae.model.crud.deprecated.extension.delete.filter;

import com.dereekb.gae.utilities.filters.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * {@link Filter} that uses a delegate to filter objects that can be deleted.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public class CanDeleteFilter<T> extends AbstractFilter<T> {

	private CanDeleteFilterDelegate<T> delegate;

	public CanDeleteFilterDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CanDeleteFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public FilterResult filterObject(T object) {
		boolean canDelete = this.delegate.canDelete(object);
		return FilterResult.withBoolean(canDelete);
	}

}
