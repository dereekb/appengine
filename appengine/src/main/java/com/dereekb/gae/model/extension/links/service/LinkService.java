package com.dereekb.gae.model.extension.links.service;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeSetException;

/**
 * Atomic services for changing links between objects.
 *
 * @author dereekb
 *
 */
public interface LinkService {

	/**
	 * Performs changes using the input {@link LinkServiceRequest}.
	 *
	 * @param request
	 *            Request for performing changes.
	 * @throws LinkServiceChangeSetException
	 *             If the changes have failed. If thrown, no changes will be
	 *             saved.
	 * @throws AtomicOperationException
	 *             if any other exceptions occur during the changes. No changes
	 *             are saved.
	 */
	public LinkServiceResponse updateLinks(LinkServiceRequest request)
	        throws LinkServiceChangeSetException,
	            AtomicOperationException;

}
