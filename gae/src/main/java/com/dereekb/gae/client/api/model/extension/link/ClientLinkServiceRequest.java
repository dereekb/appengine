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
	 * @return {@code true} if atomic.
	 */
	public boolean isAtomic();

	/**
	 * Returns the model type.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getType();

	/**
	 * Returns the list of changes to perform.
	 * 
	 * @return List of changes. Never {@code null}, nor empty.
	 */
	public List<ApiLinkChange> getChanges();

}
