package com.dereekb.gae.client.api.service.sender.impl;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.utilities.misc.path.SimplePath;

/**
 * Abstract {@link ClientRequestSender} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractClientRequestSender
        implements ClientRequestSender {

	private String baseUrl;

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		if (baseUrl == null) {
			throw new IllegalArgumentException("BaseUrl cannot be null.");
		}

		this.baseUrl = baseUrl;
	}

	// MARK: ClientRequestSender
	@Override
	public ClientResponse sendRequest(ClientRequest request) throws ClientConnectionException {
		ClientRequestUrl requestUrl = request.getUrl();

		SimplePath relativePath = requestUrl.getRelativeUrlPath();
		String fullPath = this.baseUrl + relativePath.getPath();

		// TODO: Watch hanging '/' in base URL.

		return this.sendRequest(fullPath, request);
	}

	// MARK: Abstract
	public abstract ClientResponse sendRequest(String url,
	                                           ClientRequest request);

}
