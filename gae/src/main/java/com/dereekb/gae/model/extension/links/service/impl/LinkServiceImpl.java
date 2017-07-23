package com.dereekb.gae.model.extension.links.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;
import com.dereekb.gae.model.extension.links.service.LinkServiceResponse;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeException;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeSetException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.exception.ChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.FailedLinkModificationSystemChangeException;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModificationSystemInstanceAlreadyRunException;

/**
 * {@link LinkService} implementation using the {@link LinkModificationSystem}.
 * 
 * @author dereekb
 *
 */
public class LinkServiceImpl
        implements LinkService {

	private LinkModificationSystem system;

	public LinkModificationSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkModificationSystem system) {
		if (system == null) {
			throw new IllegalArgumentException("System cannot be null.");
		}

		this.system = system;
	}

	// MARK: LinkService
	@Override
	public LinkServiceResponse updateLinks(LinkServiceRequest serviceRequest)
	        throws LinkSystemChangeSetException,
	            AtomicOperationException {

		List<LinkModificationSystemRequest> linkChanges = serviceRequest.getChangeRequests();
		
		LinkModificationSystemInstance instance = this.system.makeInstance();
		
		List<LinkSystemChangeException> requestExceptions = new ArrayList<LinkSystemChangeException>();
		
		for (LinkModificationSystemRequest request : linkChanges) {
			try {
				instance.queueRequest(request);	// Queue Requests
			} catch (UnavailableLinkException | UnavailableLinkModelException | ConflictingLinkModificationSystemRequestException | InvalidLinkModificationSystemRequestException e) {
				LinkSystemChangeException changeException = new LinkSystemChangeException(request, e);
				requestExceptions.add(changeException);
			} catch (ChangesAlreadyExecutedException e) {
				// Won't occur here...
			}
		}
		
		if (requestExceptions.isEmpty() == false) {
			throw new LinkSystemChangeSetException(requestExceptions);
		}
		
		try {
			instance.applyChangesAndCommit();
		} catch (LinkModificationSystemInstanceAlreadyRunException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FailedLinkModificationSystemChangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
