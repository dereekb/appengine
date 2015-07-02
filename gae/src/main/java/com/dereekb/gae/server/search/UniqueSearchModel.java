package com.dereekb.gae.server.search;


/**
 * An object is one that has a mutable search identifier, and is uniquely
 * indexed using the Google App Engine Search API.
 *
 * @author dereekb
 * @see {@link DocumentSearchController}
 */
public interface UniqueSearchModel {

	/**
	 * Returns the Document Identifier for this model.
	 *
	 * @return
	 */
	public String getSearchIdentifier();

	/**
	 * Sets the Document Identifier for this model.
	 *
	 * @param identifier
	 */
	public void setSearchIdentifier(String identifier);

}
