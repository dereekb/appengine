package com.dereekb.gae.model.extension.search.document.search.service;

import com.dereekb.gae.server.search.document.query.expression.Expression;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;

/**
 * Used by {@link DocumentSearchService}.
 *
 * @author dereekb
 *
 */
public interface DocumentSearchRequest {

	/**
	 * @return Index to query. Never {@code null}.
	 */
	public String getIndex();

	/**
	 * @return {@link Expression}. Never {@code null}.
	 */
	public Expression getExpression();

	/**
	 * Returns any request options.
	 *
	 * @return Options. Can be {@code null}.
	 */
	public SearchOptions getOptions();

}
