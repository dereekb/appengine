package com.dereekb.gae.server.search.model;


/**
 * {@link SearchOptions} with setters accessible.
 *
 * @author dereekb
 *
 */
public interface MutableSearchOptions
        extends SearchOptions {

	public void setCursor(String cursor);

	public void setOffset(Integer offset) throws IllegalArgumentException;

	public void setLimit(Integer limit) throws IllegalArgumentException;

}
