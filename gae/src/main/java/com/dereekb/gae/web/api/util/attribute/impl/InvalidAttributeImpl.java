package com.dereekb.gae.web.api.util.attribute.impl;

import com.dereekb.gae.utilities.web.error.ErrorInfo;
import com.dereekb.gae.utilities.web.error.impl.ErrorInfoImpl;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvalidAttributeImpl
        implements InvalidAttribute {

	private String attribute;
	private String value;
	private String detail;
	private ErrorInfoImpl error;

	public InvalidAttributeImpl() {}

	public InvalidAttributeImpl(String attribute, String value) {
		this(attribute, value, null);
	}

	public InvalidAttributeImpl(InvalidAttribute failure) {
		this(failure.getAttribute(), failure.getValue(), failure.getDetail(), failure.getError());
	}

	public InvalidAttributeImpl(String attribute, String value, String detail) {
		this.setAttribute(attribute);
		this.setValue(value);
		this.setDetail(detail);
	}

	public InvalidAttributeImpl(String attribute, String value, String detail, ErrorInfo error) {
		this.setAttribute(attribute);
		this.setValue(value);
		this.setDetail(detail);
		this.setErrorInfo(error);
	}

	@Override
	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Attribute cannot be null.");
		}

		this.attribute = attribute;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public ErrorInfoImpl getError() {
		return this.error;
	}

	public void setError(ErrorInfoImpl error) {
		this.error = error;
	}

	@JsonIgnore
	public void setErrorInfo(ErrorInfo error) {
		ErrorInfoImpl errorImpl = null;
		
		if (error != null) {
			if (error instanceof ErrorInfoImpl) {
				errorImpl = (ErrorInfoImpl) error;
			} else {
				errorImpl = new ErrorInfoImpl(error);
			}
		}

		this.setError(errorImpl);
	}

	@Override
	public String toString() {
		return "InvalidAttributeImpl [attribute=" + this.attribute + ", value=" + this.value + ", detail=" + this.detail
		        + ", error=" + this.error + "]";
	}

}
