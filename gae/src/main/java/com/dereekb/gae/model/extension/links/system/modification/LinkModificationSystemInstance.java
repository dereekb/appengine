package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.FailedLinkModificationSystemChangeException;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModificationSystemInstanceAlreadyRunException;

/**
 * {@link LinkModificationSystem} instance that can perform changes on models.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemInstance {

	/**
	 * Returns the options configuration for this instance.
	 * 
	 * @return {@link LinkModificationSystemInstance}.
	 */
	public LinkModificationSystemInstanceOptions getOptions();
	
	/**
	 * Adds a new request to be performed on this instance.
	 * <p>
	 * The request should be unique from the other models.
	 * 
	 * @param request
	 *            {@link LinkModificationSystemRequest}. Never {@code null}.
	 * @throws UnavailableLinkException
	 * @throws ChangesAlreadyExecutedException
	 * @throws ConflictingLinkModificationSystemRequestException
	 * @throws InvalidLinkModificationSystemRequestException
	 */
	public void queueRequest(LinkModificationSystemRequest request)
	        throws UnavailableLinkException,
	            UnavailableLinkModelException,
	            ChangesAlreadyExecutedException,
	            ConflictingLinkModificationSystemRequestException,
	            InvalidLinkModificationSystemRequestException;

	/**
	 * Run to apply all submitted changes.
	 * <p>
	 * Changes are automatically committed if configured as such by the options.
	 * 
	 * @throws AtomicOperationException thrown if one or more requests fails and this is configured to execute atomically.
	 * @throws FailedLinkModificationSystemChangeException
	 *             thrown if an issue is encountered while applying changes.
	 * @throws LinkModificationSystemInstanceAlreadyRunException
	 *             thrown if {@link #applyChangesAndCommit()} has already been called.
	 */
	public LinkModificationSystemChangesResult applyChanges()
	        throws AtomicOperationException,
	        	FailedLinkModificationSystemChangeException,
	            LinkModificationSystemInstanceAlreadyRunException;

}
