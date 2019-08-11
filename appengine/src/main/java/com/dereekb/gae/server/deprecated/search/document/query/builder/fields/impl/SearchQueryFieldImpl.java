package com.dereekb.gae.server.search.query.builder.fields.impl;

import com.dereekb.gae.server.deprecated.search.document.query.builder.fields.SearchQueryField;
import com.google.appengine.api.search.Field.FieldType;

/**
 * Represents a single field in a query.
 *
 * @author dereekb
 *
 */
@Deprecated
public abstract class SearchQueryFieldImpl
        implements SearchQueryField {

	private static final String DOCUMENT_CONCAT_STRING = " AND ";

	private final boolean not;
	private final FieldType type;
	private SearchQueryFieldImpl nextField;

	public SearchQueryFieldImpl(FieldType type) {
		this.type = type;
		this.not = false;
		this.nextField = null;
	}

	public SearchQueryFieldImpl(FieldType type, Boolean not) {
		this.type = type;
		this.not = not;
		this.nextField = null;
	}

	@Override
	public boolean isNot() {
		return this.not;
	}

	@Override
	public boolean isComplex() {
		boolean isComplex = (this.not);

		if (isComplex == false && this.nextField != null) {
			isComplex = this.nextField.isComplex();
		}

		return isComplex;
	}

	@Override
	public FieldType getType() {
		return this.type;
	}

	/**
	 * Makes a clone of the current query. Does not handle the nextField
	 * elements.
	 *
	 * @return Returns a clone of the current query.
	 */
	protected abstract SearchQueryFieldImpl cloneQuery();

	private void setNextField(SearchQueryFieldImpl field) {
		this.nextField = field;
	}

	/**
	 * Recursively appends the field to the last field in the linked list.
	 *
	 * @param field
	 */
	private void appendNextField(SearchQueryFieldImpl field) {
		if (this.nextField == null) {
			this.nextField = field;
		} else {
			this.nextField.appendNextField(field);
		}
	}

	/**
	 * Creates a clone of this DocumentQueryField.
	 *
	 * @return {@link SearchQueryFieldImpl}. Never {@code null}.
	 */
	public SearchQueryFieldImpl createQueryField() {
		SearchQueryFieldImpl clone = this.cloneQuery();
		SearchQueryFieldImpl nextField = null;

		if (this.nextField != null) {
			nextField = this.nextField.createQueryField();
		}

		clone.setNextField(nextField);
		return clone;
	}

	public SearchQueryFieldImpl appendField(SearchQueryFieldImpl field) {
		SearchQueryFieldImpl fieldCopy = field.createQueryField();
		SearchQueryFieldImpl clone = this.createQueryField();

		clone.appendNextField(fieldCopy);
		return clone;
	}

	protected abstract String getQueryString();

	@Override
	public String toString() {
		String queryString = this.getQueryString();

		if (this.nextField != null) {
			String subString = this.nextField.toString();
			queryString = String.format("%s%s%s", queryString, DOCUMENT_CONCAT_STRING, subString);
		}

		return queryString;
	}
}
