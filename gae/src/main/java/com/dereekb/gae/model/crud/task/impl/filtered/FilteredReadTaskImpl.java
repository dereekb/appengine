package com.dereekb.gae.model.crud.task.impl.filtered;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.task.config.ReadTaskConfig;
import com.dereekb.gae.model.crud.task.impl.ReadTaskImpl;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * {@link ReadTaskImpl} extension that filters out result models that should not
 * be returned.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class FilteredReadTaskImpl<T extends UniqueModel> extends ReadTaskImpl<T> {

	private Filter<T> filter;

	public FilteredReadTaskImpl(Getter<T> getter, Filter<T> filter) throws IllegalArgumentException {
		super(getter);
		this.setFilter(filter);
	}

	public FilteredReadTaskImpl(Getter<T> getter, ReadTaskConfig defaultConfig, Filter<T> filter)
	        throws IllegalArgumentException {
		super(getter, defaultConfig);
		this.setFilter(filter);
	}

	public Filter<T> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<T> filter) throws IllegalArgumentException {
		if (filter == null) {
			throw new IllegalArgumentException("Filter cannot be null.");
		}

		this.filter = filter;
	}

	// MARK: Override
	@Override
	protected List<T> loadModels(Set<ModelKey> set) {
		List<T> models = super.loadModels(set);
		FilterResults<T> results = this.filter.filterObjects(models);
		return results.getPassingObjects();
	}

}
