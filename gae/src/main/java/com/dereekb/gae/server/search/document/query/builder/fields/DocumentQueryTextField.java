package com.dereekb.gae.server.search.document.query.builder.fields;

import com.google.appengine.api.search.Field.FieldType;

/**
 * Helper for querying html and text fields.
 * 
 * "The only valid relational operator for text and HTML fields is equality. In
 * this case the operator means "field includes value" not "field equals value."
 * You can use the stemming operator to search for variants on a word. You can
 * also use the OR and AND operators to specify complex boolean expressions for
 * the field value. If a boolean operator appears within a quoted string, it is
 * not treated specially, it's just another piece of the character string to be
 * matched. Remember that when searching HTML fields, the text within HTML
 * markup tags is ignored."
 * 
 * @author dereekb
 * 
 */
public class DocumentQueryTextField extends DocumentQueryField {

	private static final String TEXT_FIELD_FORMAT = "%s: %s%s";
	private static final String SPECIFIC_TEXT_FIELD_FORMAT = "%s: \"%s\"";
	private static final String STEM_SYMBOL = "~";

	private boolean stemming = false;
	private boolean specificText = false;
	private final String value;
	private final String field;

	public DocumentQueryTextField(String field, String value) {
		super(FieldType.TEXT);
		this.value = value;
		this.field = field;
	}

	public DocumentQueryTextField(String field, String value, Boolean not) {
		super(FieldType.TEXT, not);
		this.value = value;
		this.field = field;
	}

	public boolean isStemming() {
		return stemming;
	}

	public void setStemming(boolean stemming) {
		this.stemming = stemming;
	}

	/**
	 * Whether or not we're searching for the specific value. The value is
	 * enclosed in quotes, so this setting is not relevant unless there is a
	 * space in the value.
	 * 
	 * For example:
	 * 
	 * "To search for a specific string of text, enclose the string in quotes.
	 * This query will retrieve documents whose Comment field contains the
	 * phrase "insanely great" (and also "insanely-great" which is tokenized to
	 * the same thing)."
	 * 
	 * @return
	 */
	public boolean isSpecificText() {
		return specificText;
	}

	public void setSpecificText(boolean specificText) {
		this.specificText = specificText;
	}

	@Override
	protected DocumentQueryField cloneQuery() {

		DocumentQueryTextField clone = new DocumentQueryTextField(this.field,
				this.value, this.isNot());

		clone.setStemming(this.stemming);
		clone.setSpecificText(this.specificText);

		return clone;
	}

	@Override
	protected String getQueryString() {

		String queryString = "";

		if (this.specificText) {
			queryString = String.format(SPECIFIC_TEXT_FIELD_FORMAT, this.field,
					this.value);
		} else {
			String stem = (this.stemming) ? STEM_SYMBOL : "";
			queryString = String.format(TEXT_FIELD_FORMAT, this.field, stem,
					this.value);
		}

		return queryString;
	}
}
