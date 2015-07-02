package com.thevisitcompany.gae.deprecated.web.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class ApiResponse {

	public static final String ERROR_REASON_KEY = "reason";
	public static final String ERROR_MESSAGE_KEY = "message";
	public static final String ERROR_EXCEPTION_KEY = "exception";
	public static final String VALIDATION_ERROR_KEY = "validation";

	@JsonInclude(Include.NON_DEFAULT)
	protected Boolean success = true;

	// TODO: Convert errors from a map to using an object that wraps the error,
	// reason/message, etc.
	protected Map<String, Object> errors;
	protected Map<String, Object> info;

	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Map<String, Object> getInfo() {
		this.initializeInfo();
		return this.info;
	}

	public boolean hasInfo(String key) {
		boolean hasInfo = false;

		if (this.info != null) {
			hasInfo = this.info.containsKey(key);
		}

		return hasInfo;
	}

	public Object readInfo(String key) {
		Map<String, Object> info = this.getInfo();
		Object infoObject = info.get(key);
		return infoObject;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public void initializeInfo() {
		if (this.info == null) {
			this.info = new HashMap<String, Object>();
		}
	}

	public void putInfo(String key,
	                    Object object) {
		this.initializeInfo();

		if (object != null) {
			this.info.put(key, object);
		} else {
			this.info.remove(key);
		}
	}

	public Map<String, Object> getErrors() {
		return this.errors;
	}

	public void setErrors(Map<String, Object> errors) {
		this.errors = errors;
	}

	public void initializeErrors() {
		if (this.errors == null) {
			this.errors = new HashMap<String, Object>();
		}
	}

	public void putError(String key,
	                     Object object) {
		this.initializeErrors();

		if (object != null) {
			this.errors.put(key, object);
		} else {
			this.errors.remove(key);
		}
	}

}
