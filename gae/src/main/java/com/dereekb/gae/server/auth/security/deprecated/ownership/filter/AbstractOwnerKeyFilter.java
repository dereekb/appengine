package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractDelegatedFilter;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * Abstract filter for owner identification.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public abstract class AbstractOwnerKeyFilter<K, T> extends AbstractDelegatedFilter<T> {

	public AbstractOwnerKeyFilter(K ownerKey) {
		this.setOwnerKey(ownerKey);
	}

	public void setOwnerKey(K ownerKey) {
		if (ownerKey == null) {
			super.setFilter(this.makeNullModelKeyFilter());
		} else {
			super.setFilter(new SetOwnerKeyFilter(ownerKey));
		}
	}

	// MARK: Internal
	protected abstract K getInputKey(T input);

	protected Filter<T> makeNullModelKeyFilter() {
		return new NullOwnerKeyFilter();
	}

	protected class NullOwnerKeyFilter extends AbstractFilter<T> {

		@Override
		public FilterResult filterObject(T object) {
			K key = AbstractOwnerKeyFilter.this.getInputKey(object);
			return FilterResult.withBoolean(key == null);
		}

	}

	protected Filter<T> makeModelKeyFilter() {
		return new NullOwnerKeyFilter();
	}

	protected class SetOwnerKeyFilter extends AbstractFilter<T> {

		private final K ownerKey;

		public SetOwnerKeyFilter(K ownerKey) {
			this.ownerKey = ownerKey;
		}

		@Override
		public FilterResult filterObject(T object) {
			K key = AbstractOwnerKeyFilter.this.getInputKey(object);
			return FilterResult.withBoolean(this.ownerKey.equals(key));
		}

	}

}
