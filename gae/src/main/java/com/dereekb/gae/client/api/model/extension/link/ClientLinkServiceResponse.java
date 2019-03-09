package com.dereekb.gae.client.api.model.extension.link;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * ClientLinkService response that contains missing keys, if any.
 * 
 * @author dereekb
 * 
 * @see ClientLinkService
 */
public interface ClientLinkServiceResponse {

	/**
	 * Returns a map containing all missing primary keys.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> getMissing();

}
