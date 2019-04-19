package com.dereekb.gae.server.initialize;

/**
 * Service used for initializing the server.
 *
 * @author dereekb
 *
 */
public interface ServerInitializeService {

	/**
	 * Performs initialization of the server.
	 */
	void initializeServer() throws Exception;

}
