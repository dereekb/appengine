package com.dereekb.gae.utilities.filters.impl;

/**
 * Filter types.
 * 
 * @author dereekb
 *
 */
public enum MergeFilterType {
	
	/**
	 * All contained filters must return true, or the result for an object is false.
	 */
	AND_FILTER,
	
	/**
	 * Only a single contained filter needs to return true for the filter to return true for an object.
	 */
	OR_FILTER

}
