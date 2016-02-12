package com.dereekb.gae.server.search.model;

/**
 * Search options that specify a cursor, limit and offset.
 * 
 * @author dereekb
 *
 */
public interface SearchOptions {

	public String getCursor();

	public Integer getLimit();

	public Integer getOffset();

}
