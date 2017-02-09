package com.dereekb.gae.client.api.service.request;

import com.dereekb.gae.utilities.misc.path.SimplePath;

/**
 * Request URL object that contains the URL as a {@link SimplePath}.
 * 
 * @author dereekb
 *
 */
public interface ClientRequestUrl {

	public String getUrl();

	public SimplePath getUrlPath();

}
