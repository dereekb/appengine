package com.dereekb.gae.utilities.model.search.request;

import com.dereekb.gae.utilities.misc.parameters.MutableParameters;

/**
 * {@link SearchOptions} with setters accessible.
 *
 * @author dereekb
 *
 */
public interface MutableSearchOptions
        extends SearchOptions, MutableParameters {

	/**
	 * Sets the results cursor.
	 * 
	 * @param cursor
	 *            {@link String}, or {@code null} to not use cursor.
	 */
	public void setCursor(String cursor);

	/**
	 * Sets the results offset.
	 * 
	 * @param limit
	 *            {@link Integer}, or {@code null} to use default.
	 * @throws IllegalArgumentException
	 *             thrown if the offset is rejected.
	 */
	public void setOffset(Integer offset) throws IllegalArgumentException;

	/**
	 * Sets the results limit.
	 * 
	 * @param limit
	 *            {@link Integer}, or {@code null} to use default.
	 * @throws IllegalArgumentException
	 *             thrown if the limit is rejected.
	 */
	public void setLimit(Integer limit) throws IllegalArgumentException;

}
