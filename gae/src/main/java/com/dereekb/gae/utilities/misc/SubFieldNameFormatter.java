package com.dereekb.gae.utilities.misc;

public class SubFieldNameFormatter {

	public static final String SUB_FIELD_FORMAT = "_%s";
	public static final String DEFAULT_FORMAT = "%s" + SUB_FIELD_FORMAT;

	private String format;
	private String field;

	public SubFieldNameFormatter(String field) {
		this(DEFAULT_FORMAT, field);
	}

	public SubFieldNameFormatter(String format, String field) {
		this.setFormat(format);
		this.setField(field);
	}

	public String getFormat() {
		return this.format;
	}

	/**
	 * Utility function for setting the prefix format for the field name. The
	 * sub field format suffix is appended.
	 *
	 * @param format
	 *            String format with atleast one "%s" in it.
	 */
	public void setFieldFormat(String format) {
		this.setFormat(format + SUB_FIELD_FORMAT);
	}

	public void setFormat(String format) throws IllegalArgumentException {
		if (format == null || format.isEmpty()) {
			throw new IllegalArgumentException("Format cannot be null or empty.");
		}

		this.format = format;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) throws IllegalArgumentException {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException("Field name cannot be null or empty.");
		}

		this.field = field;
	}

	public String format(String subField) {
		return String.format(this.format, this.field, subField);
	}

	@Override
	public String toString() {
		return "SubFieldNameFormatter [format=" + this.format + ", field=" + this.field + "]";
	}

}