package com.dereekb.gae.utilities.filters.impl;

import java.util.List;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Basic filter.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
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
		return FilterUtility.filterObjects(this, objects);
	}

	public List<T> filterAndReturnMatchingResult(Iterable<T> objects,
	                                             FilterResult result) {
		FilterResults<T> results = this.filterObjects(objects);
		List<T> filteredObjects = results.valuesForKey(result);
		return filteredObjects;
	}

	/**
	 * Does an efficient pass over input objects checking to see if they all
	 * pass the filter.
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

}
