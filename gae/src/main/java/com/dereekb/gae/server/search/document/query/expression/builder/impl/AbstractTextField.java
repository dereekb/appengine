package com.dereekb.gae.server.search.document.query.expression.builder.impl;


public abstract class AbstractTextField extends AbstractField {

	protected static final String TEXT_FIELD_FORMAT = "%s: (%s)";
	protected static final String SPECIFIC_TEXT_FIELD_FORMAT = "%s: \"%s\"";

	protected String value;
	protected boolean specificText;

	public AbstractTextField(String name, String value) {
		this(name, value, false);
	}

	public AbstractTextField(String name, String value, boolean specificText) {
		super(name);
		this.setValue(value);
		this.specificText = specificText;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSpecificText() {
		return this.specificText;
	}

	public void setSpecificText(boolean specificText) {
		this.specificText = specificText;
	}

	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public String getExpressionValue() {
		String queryString;

		if (this.specificText) {
			queryString = String.format(SPECIFIC_TEXT_FIELD_FORMAT, this.name, this.value);
		} else {

			/*
			 * Text that is unbounded by quotes will fail to parse correctly if
			 * it includes a comma.
			 *
			 * All punctuation is removed from the value.
			 */
			queryString = String.format(TEXT_FIELD_FORMAT, this.name, this.getSanitizedValue());
		}

		return queryString;
	}

	public String getSanitizedValue() {
		return this.value.replaceAll(ARGUMENT_SEPARATOR, "");
	}

}
