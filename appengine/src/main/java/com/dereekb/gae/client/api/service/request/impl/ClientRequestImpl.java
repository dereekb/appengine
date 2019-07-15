package com.dereekb.gae.client.api.service.request.impl;

import org.springframework.http.MediaType;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;

/**
 * {@link ClientRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ClientRequestImpl
        implements ClientRequest {

	public static final String CONTENT_TYPE_HEADER = "content-type";

	private ClientRequestUrl url;
	private ClientRequestMethod method;

	private Parameters headers;
	private Parameters parameters;

	private ClientRequestData data;

	public ClientRequestImpl(ClientRequestUrl url, ClientRequestMethod method) throws IllegalArgumentException {
		this.setUrl(url);
		this.setMethod(method);
	}

	public ClientRequestImpl(ClientRequestUrl url, ClientRequestMethod method, MediaType contentType) throws IllegalArgumentException {
		this(url, method);
		this.setContentType(contentType);
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
	public String getContentType() {
		if (this.headers != null) {
			return this.headers.getParameters().get(CONTENT_TYPE_HEADER);
		} else {
			return null;
		}
	}

	/**
	 * Utility function for setting the content type to {@link MediaType.APPLICATION_FORM_URLENCODED}.
	 */
	@Deprecated
	public void setFormUrlEncodedContentType() {
		this.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	}

	public void setContentType(MediaType mediaType) {
		String contentType = null;

		if (mediaType != null) {
			contentType = mediaType.toString();
		}

		this.setContentType(contentType);
	}

	public void setContentType(String contentType) {
		ParametersImpl newHeaders = null;

		if (this.headers != null) {
			newHeaders = new ParametersImpl(this.headers);
		} else {
			newHeaders = new ParametersImpl();
		}

		if (contentType == null) {
			newHeaders.removeEntry(CONTENT_TYPE_HEADER);
		} else {
			newHeaders.addEntry(CONTENT_TYPE_HEADER, contentType);
		}

		this.setHeaders(newHeaders);
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
