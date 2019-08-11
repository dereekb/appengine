package com.dereekb.gae.server.search.query.builder.fields.impl;

/**
 * Used for easily defining a global query, or an arbitrary query.
 *
 * @author dereekb
 *
 */
@Deprecated
public final class SearchQueryRawField extends SearchQueryFieldImpl {

	private final String query;

	public SearchQueryRawField(String query) {
		super(null);
		this.query = query;
	}

	public SearchQueryRawField(String query, Boolean not) {
		super(null, not);
		this.query = query;
	}

	@Override
	protected SearchQueryFieldImpl cloneQuery() {
		SearchQueryRawField copy = new SearchQueryRawField(this.query, this.isNot());
		return copy;
	}

	@Override
	protected String getQueryString() {
		return this.query;
	}

}
