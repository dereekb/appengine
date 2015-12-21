package com.dereekb.gae.model.extension.search.document.search.components;

import com.dereekb.gae.server.search.document.query.deprecated.builder.DocumentQueryBuilder;

/**
 * Used by {@link DocumentSearchQueryFactory} for decoding query requests and
 * applying it's parameters to a DocumentQueryBuilderExpression instance.
 *
 * @author dereekb
 *
 * @param <Q>
 */
public interface DocumentSearchQueryFactoryDelegate<Q extends DocumentSearchQuery> {

	/**
	 * Builds into the input {@link DocumentQueryBuilderExpression} using the input query.
	 *
	 * @param base
	 * @param query
	 * @return New {@link DocumentQueryBuilderExpression} containing all applicable
	 *         parameters from the input query.
	 */
	public DocumentQueryBuilder buildWithQuery(DocumentQueryBuilder base,
	                                           Q query);

}
