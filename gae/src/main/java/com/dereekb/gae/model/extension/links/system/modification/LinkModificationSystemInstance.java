package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;

/**
 * {@link LinkModificationSystem} instance that can perform changes on models.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemInstance {

	/**
	 * Adds a new request to be performed on this instance.
	 * 
	 * @param request
	 *            {@link LinkModificationSystemRequest}. Never {@code null}.
	 * @throws UnavailableLinkException
	 * @throws InvalidLinkModificationSystemRequestException
	 */
	public void addRequest(LinkModificationSystemRequest request)
	        throws UnavailableLinkException,
	            UnavailableLinkModelException,
	            InvalidLinkModificationSystemRequestException;

	/**
	 * Run to apply all submitted changes.
	 */
	public void applyChanges();

}
