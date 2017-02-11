package com.dereekb.gae.client.api.service.request;

import com.dereekb.gae.utilities.misc.path.SimplePath;

/**
 * Request URL object that contains the URL as a {@link SimplePath}.
 * 
 * @author dereekb
 *
 */
public interface ClientRequestUrl {

	/**
	 * Returns the relative URL path.
	 * 
	 * @return {@link SimplePath}. Never {@code null}.
	 */
	public SimplePath getRelativeUrlPath();

}
