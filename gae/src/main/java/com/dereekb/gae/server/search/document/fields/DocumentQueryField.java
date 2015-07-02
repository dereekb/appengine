package com.dereekb.gae.server.search.document.fields;

import com.google.appengine.api.search.Field.FieldType;

/**
 * Represents a single field in a query.
 * 
 * @author dereekb
 * 
 */
public abstract class DocumentQueryField {

	private static final String DOCUMENT_CONCAT_STRING = " AND ";

	private final Boolean not;
	private final FieldType type;
	private DocumentQueryField nextField;

	public DocumentQueryField(FieldType type) {
		this.type = type;
		this.not = false;
		this.nextField = null;
	}

	public DocumentQueryField(FieldType type, Boolean not) {
		this.type = type;
		this.not = not;
		this.nextField = null;
	}

	public Boolean isNot() {
		return not;
	}

	public Boolean isComplex() {
		boolean isComplex = (this.not);

		if (isComplex == false && this.nextField != null) {
			isComplex = this.nextField.isComplex();
		}

		return isComplex;
	}

	public FieldType getType() {
		return type;
	}

	/**
	 * Makes a clone of the current query. Does not handle the nextField
	 * elements.
	 * 
	 * @return Returns a clone of the current query.
	 */
	protected abstract DocumentQueryField cloneQuery();

	private void setNextField(DocumentQueryField field) {
		this.nextField = field;
	}

	/**
	 * Recursively appends the field to the last field in the linked list.
	 * 
	 * @param field
	 */
	private void appendNextField(DocumentQueryField field) {
		if (this.nextField == null) {
			this.nextField = field;
		} else {
			this.nextField.appendNextField(field);
		}
	}

	/**
	 * Creates a clone of this DocumentQueryField.
	 * 
	 * @return
	 */
	public DocumentQueryField createQueryField() {

		DocumentQueryField clone = this.cloneQuery();
		DocumentQueryField nextField = null;

		if (this.nextField != null) {
			nextField = this.nextField.createQueryField();
		}

		clone.setNextField(nextField);
		return clone;
	}

	public DocumentQueryField appendField(DocumentQueryField field) {
		DocumentQueryField fieldCopy = field.createQueryField();
		DocumentQueryField clone = this.createQueryField();

		clone.appendNextField(fieldCopy);
		return clone;
	}

	protected abstract String getQueryString();

	public String toString() {
		String queryString = this.getQueryString();

		if (this.nextField != null) {
			String subString = this.nextField.toString();
			queryString = String.format("%s%s%s", queryString, DOCUMENT_CONCAT_STRING, subString);
		}

		return queryString;
	}
}
