package com.dereekb.gae.server.search.document.query.deprecated;

/**
 * Sanitizes query strings.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface DocumentQueryStringSanitizer {

	/**
	 * Sanitizes an input query string, removing all disallowed values and returning a clean string.
	 *
	 * @param query
	 * @return
	 */
	public String sanitizeQuery(String query);

}
