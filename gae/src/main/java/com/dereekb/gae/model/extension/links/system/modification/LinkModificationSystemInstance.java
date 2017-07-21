package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.FailedLinkModificationSystemChangeException;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModificationSystemRunnerAlreadyRunException;

/**
 * {@link LinkModificationSystem} instance that can perform changes on models.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemInstance {

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
	 * 
	 * @throws FailedLinkModificationSystemChangeException
	 *             thrown if an issue is encountered while applying changes.
	 * @throws LinkModificationSystemRunnerAlreadyRunException
	 *             thrown if {@link #applyChangesAndCommit()} has already been called.
	 */
	public LinkModificationSystemChangesResult applyChangesAndCommit()
	        throws FailedLinkModificationSystemChangeException,
	            LinkModificationSystemRunnerAlreadyRunException;

	/**
	 * Run to apply all submitted changes.
	 * 
	 * @throws FailedLinkModificationSystemChangeException
	 *             thrown if an issue is encountered while applying changes.
	 * @throws LinkModificationSystemRunnerAlreadyRunException
	 *             thrown if {@link #applyChangesAndCommit()} has already been called.
	 */
	public LinkModificationSystemChangesResult applyChanges()
	        throws FailedLinkModificationSystemChangeException,
	            LinkModificationSystemRunnerAlreadyRunException;

}
