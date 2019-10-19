package com.dereekb.gae.server.search.query.builder.fields.impl;

import com.google.appengine.api.search.Field.FieldType;

/**
 * Atom field. A string that should be matched exactly.
 *
 * @author dereekb
 *
 * @see SearchQueryTextField
 */
@Deprecated
public class SearchQueryAtomField extends SearchQueryFieldImpl {

	private static final String ATOM_FIELD_FORMAT = "%s: \"%s\"";

	private final String value;
	private final String field;

	public SearchQueryAtomField(String field, String value) {
		super(FieldType.ATOM);
		this.value = value;
		this.field = field;
	}

	public SearchQueryAtomField(String field, String value, Boolean not) {
		super(FieldType.ATOM, not);
		this.value = value;
		this.field = field;
	}

	@Override
	protected SearchQueryFieldImpl cloneQuery() {
		SearchQueryAtomField clone = new SearchQueryAtomField(this.field, this.value, this.isNot());
		return clone;
	}

	@Override
	protected String getQueryString() {
		String queryString = String.format(ATOM_FIELD_FORMAT, this.field, this.value);
		return queryString;
	}

}
