package com.dereekb.gae.utilities.validation;

import javax.validation.ValidationException;

/**
 * {@link Exception} used for validation.
 *
 * @author dereekb
 *
 */
public class ContentValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;

	private String field;
	private Object value;

	public ContentValidationException() {
		super();
	}

	public ContentValidationException(String field, Object value, String message) {
		super(message);
		this.field = field;
		this.value = value;
	}

	public ContentValidationException(String message) {
		super(message);
	}

	public ContentValidationException(Throwable cause) {
		super(cause);
	}

	public ContentValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ContentValidationException [field=" + this.field + ", value=" + this.value + ", message="
		        + this.getMessage()
		        + "]";
	}

}
