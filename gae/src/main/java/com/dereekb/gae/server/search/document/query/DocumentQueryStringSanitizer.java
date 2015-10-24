package com.dereekb.gae.server.search.document.query;

/**
 * Sanitizes query strings.
 * 
 * @author dereekb
 * 
 */
public interface DocumentQueryStringSanitizer {

	/**
	 * Sanitizes an input query string, removing all disallowed values and returning a clean string.
	 * 
	 * @param query
	 * @return
	 */
	public String sanitizeQuery(String query);

}
