package com.dereekb.gae.client.api.service.response.impl;

import com.dereekb.gae.client.api.service.response.ClientResponse;

/**
 * {@link ClientResponse} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientResponseImpl
        implements ClientResponse {

	private int status;
	private String responseData;

	public ClientResponseImpl(int status, String responseData) {
		this.setStatus(status);
		this.setResponseData(responseData);
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	// MARK: ClientResponse
	@Override
	public boolean getSuccess() {
		return this.status == 200;
	}

	@Override
	public int getStatus() {
		return this.status;
	}

	@Override
	public String getResponseData() {
		return this.responseData;
	}

	@Override
	public String toString() {
		return "ClientResponseImpl [status=" + this.status + ", responseData=" + this.responseData + "]";
	}

}
