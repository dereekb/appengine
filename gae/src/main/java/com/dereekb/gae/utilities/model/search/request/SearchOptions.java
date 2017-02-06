package com.dereekb.gae.utilities.model.search.request;

import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * Search options that specify a cursor, limit and offset.
 *
 * @author dereekb
 *
 */
public interface SearchOptions
        extends Parameters {

	/**
	 * Returns the query cursor.
	 *
	 * @return {@link String} cursor. {@link null} if not specified.
	 */
	public String getCursor();

	/**
	 * Returns the max number of elements to retrieve.
	 *
	 * @return the limit. {@link null} if not specified.
	 */
	public Integer getLimit();

	/**
	 * Returns the number of elements to skip.
	 *
	 * @return the offset. {@link null} if not specified.
	 */
	public Integer getOffset();

}
