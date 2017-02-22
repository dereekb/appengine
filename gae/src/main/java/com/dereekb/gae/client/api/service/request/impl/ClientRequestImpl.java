package com.dereekb.gae.client.api.service.request.impl;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link ClientRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientRequestImpl
        implements ClientRequest {

	private ClientRequestUrl url;
	private ClientRequestMethod method;

	private Parameters headers;
	private Parameters parameters;

	private ClientRequestData data;

	public ClientRequestImpl(ClientRequestUrl url, ClientRequestMethod method) throws IllegalArgumentException {
		this.setUrl(url);
		this.setMethod(method);
	}

	@Override
	public ClientRequestUrl getUrl() {
		return this.url;
	}

	public void setUrl(ClientRequestUrl url) {
		if (url == null) {
			throw new IllegalArgumentException("url cannot be null.");
		}

		this.url = url;
	}

	@Override
	public ClientRequestMethod getMethod() {
		return this.method;
	}

	public void setMethod(ClientRequestMethod method) {
		if (method == null) {
			throw new IllegalArgumentException("method cannot be null.");
		}

		this.method = method;
	}

	@Override
	public Parameters getHeaders() {
		return this.headers;
	}

	public void setHeaders(Parameters headers) {
		this.headers = headers;
	}

	@Override
	public Parameters getParameters() {
		return this.parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public ClientRequestData getData() {
		return this.data;
	}

	public void setData(ClientRequestData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ClientRequestImpl [url=" + this.url + ", method=" + this.method + ", headers=" + this.headers
		        + ", parameters=" + this.parameters + ", data=" + this.data + "]";
	}

}
