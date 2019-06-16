package com.dereekb.gae.model.extension.search.document.search.query.impl;

import java.util.Map;

import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.ExpressionBuilderSource;

/**
 * Abstract search used by {@link AbstractSearchBuilderImpl}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractSearchImpl
        implements ExpressionBuilderSource {

	/**
	 * Simple text search string.
	 */
	protected String query;

	protected AbstractSearchImpl() {}

	protected AbstractSearchImpl(Map<String, String> parameters) {
		this.applyParameters(parameters);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 *
	 * @param parameters
	 */
	public abstract void applyParameters(Map<String, String> parameters);

}
