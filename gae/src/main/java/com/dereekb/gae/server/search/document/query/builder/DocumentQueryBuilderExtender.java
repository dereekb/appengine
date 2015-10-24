package com.dereekb.gae.server.search.document.query.builder;


/**
 * Used for extending an input {@link DocumentQueryBuilder}
 * 
 * @author dereekb
 *
 */
public interface DocumentQueryBuilderExtender {

	/**
	 * Extends the input document query arbitrarily.
	 * 
	 * @param query
	 * @return The new query, and never null.
	 */
	public DocumentQueryBuilder extendQuery(DocumentQueryBuilder query);

}
