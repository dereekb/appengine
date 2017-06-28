package com.dereekb.gae.utilities.web.error.impl;

import com.dereekb.gae.utilities.web.error.ErrorInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ErrorInfo} implementation.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorInfoImpl
        implements ErrorInfo {

	private String code;

	private String title;
	private String detail;

	public ErrorInfoImpl() {}

	public ErrorInfoImpl(ErrorInfo info) {
		this(info.getErrorCode(), info.getErrorTitle(), info.getErrorDetail());
	}

	public ErrorInfoImpl(String code) throws IllegalArgumentException {
		this(code, null);
	}

	public ErrorInfoImpl(String code, String title) throws IllegalArgumentException {
		this(code, title, null);
	}

	public ErrorInfoImpl(String code, String title, String detail) throws IllegalArgumentException {
		this.setCode(code);
		this.setTitle(title);
		this.setDetail(detail);
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		if (code == null) {
			throw new IllegalArgumentException("Code cannot be null.");
		}

		this.code = code;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	// MARK: ErrorInfo
	@JsonIgnore
	@Override
	public String getErrorCode() {
		return this.code;
	}

	@JsonIgnore
	@Override
	public String getErrorTitle() {
		return this.title;
	}

	@JsonIgnore
	@Override
	public String getErrorDetail() {
		return this.detail;
	}

	// MARK: AlwaysKeyed
	@JsonIgnore
	@Override
	public String keyValue() {
		return this.code;
	}

	@Override
	public String toString() {
		return "ErrorInfoImpl [code=" + this.code + ", title=" + this.title + ", detail=" + this.detail + "]";
	}

}
