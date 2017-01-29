package com.dereekb.gae.server.datastore.models.keys.filter;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * Abstract {@link Filter} that filters by comparing models keys.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelKeyFilter<T> extends AbstractFilter<T> {

	private ModelKey modelKey;

	public AbstractModelKeyFilter(ModelKey modelKey) {
		this.setModelKey(modelKey);
	}

	public ModelKey getModelKey() {
		return this.modelKey;
	}

	public void setModelKey(ModelKey modelKey) {
		this.modelKey = modelKey;
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(T object) {
		ModelKey key = this.readModelKey(object);
		return FilterResult.withBoolean(ModelKey.isEqual(key, this.modelKey));
	}

	protected abstract ModelKey readModelKey(T model);

}
