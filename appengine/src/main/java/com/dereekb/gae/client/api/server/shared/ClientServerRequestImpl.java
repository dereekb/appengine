package com.dereekb.gae.client.api.server.shared;

/**
 * {@link ClientServerRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ClientServerRequestImpl
        implements ClientServerRequest {

	private String requestUrl;

	public ClientServerRequestImpl() {}

	public ClientServerRequestImpl(String requestUrl) {
		this.setRequestUrl(requestUrl);
	}

	@Override
	public String getRequestUrl() {
		return this.requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		if (requestUrl == null) {
			throw new IllegalArgumentException("requestUrl cannot be null.");
		}

		this.requestUrl = requestUrl;
	}

	@Override
	public String toString() {
		return "ClientServerRequestImpl [requestUrl=" + this.requestUrl + "]";
	}

}
