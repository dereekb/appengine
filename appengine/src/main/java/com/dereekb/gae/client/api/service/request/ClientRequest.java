package com.dereekb.gae.client.api.service.request;

import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * A configured HTTP request.
 *
 * @author dereekb
 *
 */
public interface ClientRequest {

	/**
	 * Returns the URL.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public ClientRequestUrl getUrl();

	/**
	 * Returns the client method type.
	 *
	 * @return {@link ClientRequestMethod}. Never {@code null}.
	 */
	public ClientRequestMethod getMethod();

	/**
	 * Returns the content-type header from all headers, if specified.
	 *
	 * @return {@link String}, or {@code null} if not specified.
	 */
	public String getContentType();

	/**
	 * Returns the headers for this request, if any.
	 *
	 * @return {@link Parameters}, or {@code null} if none defined.
	 */
	public Parameters getHeaders();

	/**
	 * Returns the parameters for this request, if any.
	 *
	 * @return {@link Parameters}, or {@code null} if none defined.
	 */
	public Parameters getParameters();

	/**
	 * Returns the body data for the request.
	 *
	 * @return {@link ClientRequestData}, or {@code null} if none defined.
	 */
	public ClientRequestData getData();

}
