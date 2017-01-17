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

	public void setCursor(String cursor);

	public void setOffset(Integer offset) throws IllegalArgumentException;

	public void setLimit(Integer limit) throws IllegalArgumentException;

}
