package com.dereekb.gae.client.api.model.extension.link.exception;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;

/**
 * Error used by {@link ClientLinkServiceChangeException}.
 * 
 * @author dereekb
 *
 */
public interface ClientLinkSystemChangeError {

	/**
	 * Returns the change.
	 * 
	 * @return {@link LinkModificationSystemRequest}. Never {@code null}.
	 */
	public LinkModificationSystemRequest getChange();

	public LinkExceptionReason getReason();

}