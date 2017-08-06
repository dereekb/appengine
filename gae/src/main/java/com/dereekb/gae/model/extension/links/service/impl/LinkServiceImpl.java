package com.dereekb.gae.model.extension.links.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;
import com.dereekb.gae.model.extension.links.service.LinkServiceResponse;
import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeException;
import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeSetException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangesResult;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemResult;
import com.dereekb.gae.model.extension.links.system.modification.exception.failure.LinkModificationFailedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.ChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.LinkModificationSystemInstanceAlreadyRunException;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.UnexpectedLinkModificationSystemChangeException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemInstanceOptionsImpl;
import com.dereekb.gae.utilities.collections.pairs.SuccessPair;
import com.dereekb.gae.utilities.collections.pairs.impl.SuccessResultsPair;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;

/**
 * {@link LinkService} implementation using the {@link LinkModificationSystem}.
 * 
 * @author dereekb
 *
 */
public class LinkServiceImpl
        implements LinkService {

	private LinkModificationSystem system;

	public LinkServiceImpl(LinkModificationSystem system) {
		this.setSystem(system);
	}

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
	        throws LinkServiceChangeSetException,
	            AtomicOperationException {

		LinkModificationSystemInstanceOptionsImpl options = new LinkModificationSystemInstanceOptionsImpl();
		options.setAtomic(serviceRequest.isAtomic());

		LinkModificationSystemInstance instance = this.system.makeInstance(options);

		List<LinkModificationSystemRequest> linkChanges = serviceRequest.getChangeRequests();
		List<LinkServiceChangeException> requestExceptions = new ArrayList<LinkServiceChangeException>();

		for (LinkModificationSystemRequest request : linkChanges) {
			try {
				instance.queueRequest(request);	// Queue Requests
			} catch (UnavailableLinkException | UnavailableLinkModelException
			        | ConflictingLinkModificationSystemRequestException
			        | InvalidLinkModificationSystemRequestException e) {
				LinkServiceChangeException changeException = new LinkServiceChangeException(request, e);
				requestExceptions.add(changeException);
			} catch (ChangesAlreadyExecutedException e) {
				// Won't occur here...
			}
		}

		if (requestExceptions.isEmpty() == false) {
			throw new LinkServiceChangeSetException(requestExceptions);
		}

		LinkModificationSystemChangesResult changesResult = null;

		try {
			changesResult = instance.applyChanges();
		} catch (LinkModificationSystemInstanceAlreadyRunException e) {
			// Won't occur.
		} catch (LinkModificationFailedException e) {
			throw LinkServiceChangeSetException.make(e);
		} catch (UnexpectedLinkModificationSystemChangeException e) {
			throw e;
		}

		return new LinkServiceResponseImpl(changesResult);
	}

	// MARK: LinkServiceResponse
	private class LinkServiceResponseImpl
	        implements LinkServiceResponse {

		private final LinkModificationSystemChangesResult changesResult;
		
		private transient LinkServiceChangeSetException changeSet;
		private transient List<SuccessPair<LinkModificationSystemResult>> successResults;
		
		public LinkServiceResponseImpl(LinkModificationSystemChangesResult changesResult) {
			this.changesResult = changesResult;
		}

		// MARK: LinkServiceResponse
		@Override
		public List<SuccessPair<LinkModificationSystemResult>> getSuccessResults() {
			if (this.successResults == null) {
				this.successResults = new ArrayList<SuccessPair<LinkModificationSystemResult>>();

				List<LinkModificationSystemResult> systemResults = this.changesResult.getResults();
				
				for (LinkModificationSystemResult systemResult : systemResults) {
					SuccessPair<LinkModificationSystemResult> pair = SuccessResultsPair.make(systemResult);
					this.successResults.add(pair);
				}
			}
			
			return this.successResults;
		}

		@Override
		public LinkServiceChangeSetException getErrorsSet() {
			if (this.changeSet == null) {
				this.changeSet = this.buildErrorsSet();
			}
			
			return this.changeSet;
		}
		
		public LinkServiceChangeSetException buildErrorsSet() {
			
			List<LinkModificationSystemResult> systemResults = this.changesResult.getResults();
			List<LinkServiceChangeException> requestExceptions = new ArrayList<LinkServiceChangeException>();
			
			for (LinkModificationSystemResult systemResult : systemResults) {
				ApiResponseErrorConvertable convertable = systemResult.getError();
				
				if (convertable != null) {
					LinkModificationSystemRequest request = systemResult.getRequest();
					
					LinkServiceChangeException changeException = new LinkServiceChangeException(request, convertable);
					requestExceptions.add(changeException);
				}
			}
			
			return new LinkServiceChangeSetException(requestExceptions);
		}

	}

}
