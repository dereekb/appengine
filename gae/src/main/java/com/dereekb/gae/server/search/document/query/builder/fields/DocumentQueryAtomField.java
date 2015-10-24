package com.dereekb.gae.server.search.document.query.builder.fields;

import com.google.appengine.api.search.Field.FieldType;

public class DocumentQueryAtomField extends DocumentQueryField {

	private static final String ATOM_FIELD_FORMAT = "%s: \"%s\"";

	private final String value;
	private final String field;

	public DocumentQueryAtomField(String field, String value) {
		super(FieldType.ATOM);
		this.value = value;
		this.field = field;
	}

	public DocumentQueryAtomField(String field, String value, Boolean not) {
		super(FieldType.ATOM, not);
		this.value = value;
		this.field = field;
	}

	@Override
	protected DocumentQueryField cloneQuery() {
		DocumentQueryAtomField clone = new DocumentQueryAtomField(this.field, this.value,
				this.isNot());

		return clone;
	}

	@Override
	protected String getQueryString() {
		String queryString =  String.format(ATOM_FIELD_FORMAT, this.field, this.value);
		return queryString;
	}
}
