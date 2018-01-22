package com.dereekb.gae.web.api.server.initialize;

import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * {@link ApiInitializeServerController} delegate.
 *
 * @author dereekb
 *
 */
public interface ApiInitializeServerControllerDelegate {

	/**
	 * Initializes the server.
	 *
	 * @return {@link ApiResponse}.
	 */
	public ApiResponse initialize() throws RuntimeException;

}
