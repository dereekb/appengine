package com.dereekb.gae.utilities.filters.impl;

import java.util.List;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * {@link Filter} that incoporates multiple other filters.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class MergeFilterImpl<T> extends AbstractFilter<T> {

	private List<Filter<T>> filters;
	private MergeFilterType filterType;

	private transient Filter<T> internalFilter;

	public MergeFilterImpl(List<Filter<T>> filters, MergeFilterType filterType) {
		super();
		this.setFilters(filters);
		this.setFilterType(filterType);
	}

	public List<Filter<T>> getFilters() {
		return this.filters;
	}

	public void setFilters(List<Filter<T>> filters) {
		if (filters == null) {
			throw new IllegalArgumentException("filters cannot be null.");
		}

		this.filters = filters;
	}

	public MergeFilterType getFilterType() {
		return this.filterType;
	}

	public void setFilterType(MergeFilterType filterType) {
		if (filterType == null) {
			throw new IllegalArgumentException("filterType cannot be null.");
		}

		if (this.filterType != filterType) {
			this.filterType = filterType;

			switch (filterType) {
				case AND_FILTER:
					this.internalFilter = new AndMergeFilter();
					break;
				case OR_FILTER:
					this.internalFilter = new OrMergeFilter();
					break;
				default:
					throw new UnsupportedOperationException();
			}
		}
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(T object) {
		return this.internalFilter.filterObject(object);
	}

	@Override
	public FilterResults<T> filterObjects(Iterable<? extends T> objects) {
		return this.internalFilter.filterObjects(objects);
	}

	// MARK: Internal
	private final class AndMergeFilter extends AbstractFilter<T> {

		@Override
		public FilterResult filterObject(T object) {
			for (Filter<T> filter : MergeFilterImpl.this.filters) {
				if (filter.filterObject(object) == FilterResult.FAIL) {
					return FilterResult.FAIL;
				}
			}

			return FilterResult.PASS;
		}

	}

	private final class OrMergeFilter extends AbstractFilter<T> {

		@Override
		public FilterResult filterObject(T object) {
			for (Filter<T> filter : MergeFilterImpl.this.filters) {
				if (filter.filterObject(object) == FilterResult.PASS) {
					return FilterResult.PASS;
				}
			}

			return FilterResult.FAIL;
		}

	}

}
