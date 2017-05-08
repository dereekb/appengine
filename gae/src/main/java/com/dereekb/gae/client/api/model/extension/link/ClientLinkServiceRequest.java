package com.dereekb.gae.client.api.model.extension.link;

import java.util.List;

import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;

/**
 * {@link ClientListService} request.
 * 
 * @author dereekb
 *
 */
public interface ClientLinkServiceRequest {

	/**
	 * Whether or not to change links atomically.
	 * 
	 * @return {@code boolean}.
	 */
	public boolean isAtomic();

	/**
	 * @return List of changes. Never {@code null}.
	 */
	public List<ApiLinkChange> getChanges();

}
