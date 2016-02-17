package com.dereekb.gae.utilities.filters;

import java.util.List;

/**
 * Basic filter.
 *
 * @author dereekb
 *
 * @param <T>
 */
public abstract class AbstractFilter<T>
        implements Filter<T> {

	public enum PreFilterResults {
		PASS_ALL,
		CONTINUE,
		FAIL_ALL
	}

	@Override
    public abstract FilterResult filterObject(T object);

	/**
	 * Runs a pre-filter check to see the entire filter can be skipped or not.
	 *
	 * @param objects
	 * @return PreFilterResults
	 */
	public PreFilterResults preFilterCheck(Iterable<T> objects) {
		return PreFilterResults.CONTINUE;
	}

	@Override
	public FilterResults<T> filterObjects(Iterable<? extends T> objects) {
		FilterResults<T> results = new FilterResults<T>();

		for (T object : objects) {
			FilterResult result = this.filterObject(object);
			results.add(result, object);
		}

		return results;
	}

	public List<T> filterAndReturnMatchingResult(Iterable<T> objects,
	                                             FilterResult result) {
		FilterResults<T> results = this.filterObjects(objects);
		List<T> filteredObjects = results.valuesForKey(result);
		return filteredObjects;
	}

	/**
	 * Does an efficient pass over input objects checking to see if they all pass the filter.
	 *
	 *
	 * @param objects
	 * @return True if all objects pass the filter.
	 */
	public boolean allObjectsPassFilter(Iterable<T> objects) {
		PreFilterResults preFilterCheckResults = this.preFilterCheck(objects);

		boolean allPassFilter = true;
		boolean shouldSkipChecks = true;

		switch (preFilterCheckResults) {
			case PASS_ALL: {
				shouldSkipChecks = true;
				allPassFilter = true;
			}
				break;
			case FAIL_ALL: {
				shouldSkipChecks = true;
				allPassFilter = false;
			}
				break;
			case CONTINUE: {
				shouldSkipChecks = false;
			}
				break;
		}

		if (shouldSkipChecks == false) {
			for (T object : objects) {
				FilterResult result = this.filterObject(object);

				if (result != FilterResult.PASS) {
					allPassFilter = false;
					break;
				}
			}
		}

		return allPassFilter;
	}

	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(Iterable<? extends W> sources,
	                                                      FilterDelegate<T, W> delegate) {
		FilterResults<W> results = new FilterResults<W>();

		for (W source : sources) {
			T object = delegate.getModel(source);
			FilterResult result = this.filterObject(object);
			results.add(result, source);
		}

		return results;
	}

}
