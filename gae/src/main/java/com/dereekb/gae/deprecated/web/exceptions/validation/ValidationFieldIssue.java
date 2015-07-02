package com.thevisitcompany.gae.deprecated.web.exceptions.validation;

import org.springframework.validation.FieldError;

public class ValidationFieldIssue {

	private String field;

	private String message;

	public ValidationFieldIssue() {}

	public ValidationFieldIssue(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ValidationFieldIssue [field=" + field + ", message=" + message + "]";
	}

	public static ValidationFieldIssue withError(FieldError error) {
		ValidationFieldIssue issue = new ValidationFieldIssue();
		issue.setField(error.getField());
		return issue;
	}

}
