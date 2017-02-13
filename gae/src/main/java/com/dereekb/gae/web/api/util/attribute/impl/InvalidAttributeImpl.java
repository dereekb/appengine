package com.dereekb.gae.web.api.util.attribute.impl;

import com.dereekb.gae.web.api.util.attribute.AttributeUpdateFailure;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * {@link AttributeUpdateFailure} implementation.
 * 
 * @author dereekb
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvalidAttributeImpl
        implements AttributeUpdateFailure {

	private String attribute;
	private String value;
	private String detail;

	public InvalidAttributeImpl(String attribute, String value) {
		this(attribute, value, null);
	}

	public InvalidAttributeImpl(AttributeUpdateFailure failure) {
		this(failure.getAttribute(), failure.getValue(), failure.getDetail());
	}

	public InvalidAttributeImpl(String attribute, String value, String detail) {
		this.setAttribute(attribute);
		this.setValue(value);
		this.setDetail(detail);
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
	public String toString() {
		return "AttributeUpdateFailureImpl [attribute=" + this.attribute + ", value=" + this.value + ", detail="
		        + this.detail + "]";
	}

}
