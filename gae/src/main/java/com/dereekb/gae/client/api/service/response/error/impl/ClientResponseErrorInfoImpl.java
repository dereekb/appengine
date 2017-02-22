package com.dereekb.gae.client.api.service.response.error.impl;

import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.utilities.web.error.impl.ErrorInfoImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ClientResponseErrorInfo} implementation.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientResponseErrorInfoImpl extends ErrorInfoImpl
        implements ClientResponseErrorInfo {

	private JsonNode errorData;

	@Override
	public JsonNode getErrorData() {
		return this.errorData;
	}

	public void setErrorData(JsonNode errorData) {
		this.errorData = errorData;
	}

	@Override
	public String toString() {
		return "ClientResponseErrorInfoImpl [errorData=" + this.errorData + ", getCode()=" + this.getCode()
		        + ", getTitle()=" + this.getTitle() + ", getDetail()=" + this.getDetail() + "]";
	}

}