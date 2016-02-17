package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.List;

import com.dereekb.gae.web.api.model.extension.search.ApiSearchUpdateRequest;

/**
 * {@link ApiSearchUpdateRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ApiSearchUpdateRequestImpl
        implements ApiSearchUpdateRequest {

	private String type;
	private List<String> targetKeys;

	public ApiSearchUpdateRequestImpl() {}

	public ApiSearchUpdateRequestImpl(String type, List<String> targetKeys) throws IllegalArgumentException {
		this.setType(type);
		this.setTargetKeys(targetKeys);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) throws IllegalArgumentException {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Type cannot be null or empty.");
		}

		this.type = type;
	}

	public List<String> getTargetKeys() {
		return this.targetKeys;
	}

	public void setTargetKeys(List<String> targetKeys) throws IllegalArgumentException {
		if (targetKeys == null) {
			throw new IllegalArgumentException("Target keys cannot be null.");
		}

		this.targetKeys = targetKeys;
	}

	// MARK: ApiSearchUpdateRequest
	@Override
	public String getUpdateTargetType() {
		return this.type;
	}

	@Override
	public List<String> getUpdateTargetKeys() {
		return this.targetKeys;
	}

	@Override
	public String toString() {
		return "ApiSearchUpdateRequestImpl [type=" + this.type + ", targetKeys=" + this.targetKeys + "]";
	}

}
