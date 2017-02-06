package com.dereekb.gae.server.search;

/**
 * An object is one that has a mutable search identifier, and is uniquely
 * indexed using the Google App Engine Search API.
 *
 * @author dereekb
 */
public interface UniqueSearchModel {

	/**
	 * Returns the Document Identifier for this model.
	 *
	 * @return {@link String} identifier if available, {@code null} otherwise.
	 */
	public String getSearchIdentifier();

	/**
	 * Sets the Document Identifier for this model.
	 *
	 * @param identifier
	 *            {@link String} identifier, or {@code null} to clear.
	 */
	public void setSearchIdentifier(String identifier);

}
