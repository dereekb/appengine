package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationImpl;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.TooManyChangeKeysException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModificationSystem} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemImpl
        implements LinkModificationSystem {

	private LinkSystem linkSystem;

	public LinkSystem getLinkSystem() {
		return this.linkSystem;
	}

	public void setLinkSystem(LinkSystem linkSystem) {
		if (linkSystem == null) {
			throw new IllegalArgumentException("linkSystem cannot be null.");
		}

		this.linkSystem = linkSystem;
	}

	// MARK: LinkModificationSystem
	@Override
	public LinkModificationSystemInstance makeInstance() {
		return new LinkModificationSystemInstanceImpl();
	}

	// MARK: Instance
	protected class LinkModificationSystemInstanceImpl
	        implements LinkModificationSystemInstance {

		private final List<LinkModificationSystemRequest> requests = new ArrayList<LinkModificationSystemRequest>();

		// MARK: LinkModificationSystemInstance
		@Override
		public void addRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkException,
		            UnavailableLinkModelException,
		            InvalidLinkModificationSystemRequestException {
			this.validateRequest(request);
			this.requests.add(request);
		}

		@Override
		public void applyChanges() {
			LinkModificationSystemChangesRunner runner = LinkModificationSystemImpl.this.makeRunner(this.requests);
			runner.run();
		}

		// MARK: Internal
		private void validateRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkModelException,
		            UnavailableLinkException,
		            TooManyChangeKeysException {

			LinkInfo linkInfo = LinkModificationSystemImpl.this.getLinkInfoForRequest(request);

			// Throw if multiple items are passed to a single item link.
			if (linkInfo.getLinkSize() == LinkSize.ONE && request.getKeys().size() > 1) {
				throw new TooManyChangeKeysException(request);
			}

			// TODO: Validate further for the request. (If there are multiple
			// keys for a single key, throw an exception, etc.)
		}

	}

	protected LinkModificationSystemChangesRunner makeRunner(List<LinkModificationSystemRequest> requests) {
		List<RequestChanges> requestChanges = new ArrayList<RequestChanges>();

		for (LinkModificationSystemRequest request : requests) {
			RequestChanges requestChange = this.makeChangesForRequest(request);
			requestChanges.add(requestChange);
		}

		return new LinkModificationSystemChangesRunner(requestChanges);
	}

	/**
	 * Makes all initial changes for the input request.
	 * <p>
	 * These changes do not
	 * 
	 * @param request
	 * @return
	 */
	protected RequestChanges makeChangesForRequest(LinkModificationSystemRequest request) {
		LinkInfo info = this.getLinkInfoForRequest(request);
		MutableLinkChange change = MutableLinkChangeImpl.make(request.getLinkChangeType(), request.getKeys());

		LinkModification primaryModification = new LinkModificationImpl(request.getPrimaryKey(), info, change);
		List<LinkModification> secondaryModifications = null;

		try {
			Relation relation = info.getRelationInfo();
			secondaryModifications = this.buildSecondaryModifications(relation, request);
		} catch (NoRelationException e) {

			// No relation. Create no additional components.
			secondaryModifications = Collections.emptyList();
		}

		return new RequestChanges(primaryModification, secondaryModifications);
	}

	private List<LinkModification> buildSecondaryModifications(Relation relation,
	                                                           LinkModificationSystemRequest request) {
		boolean isOptional = false;

		/*
		 * Set isOptional based on changes being made.
		 * 
		 * Removal requests that cannot load the target model are ignored will
		 * not caused the transaction changes to fail.
		 */
		MutableLinkChangeType linkChangeType = request.getLinkChangeType();

		switch (linkChangeType) {
			case ADD:
			case SET:
				isOptional = false;
				break;
			case CLEAR:
			case REMOVE:
				isOptional = true;
				break;
			default:
				break;
		}

		List<LinkModification> modifications = null;

		switch (relation.getRelationSize()) {
			case MANY_TO_MANY:
			case MANY_TO_ONE:
			case ONE_TO_MANY:
			case ONE_TO_ONE:
				// Perform "mirrored" changes on all children.
				modifications = new ArrayList<LinkModification>();

				LinkInfo relationLink = relation.getRelationLink();
				MutableLinkChange relationChange = MutableLinkChangeImpl.make(linkChangeType, request.getPrimaryKey());

				for (ModelKey key : request.getKeys()) {
					LinkModification modification = new LinkModificationImpl(key, relationLink, relationChange,
					        isOptional);
					modifications.add(modification);
				}

				break;
			case ONE_TO_NONE:
				// Do nothing.
				modifications = Collections.emptyList();
				break;
			default:
				throw new UnsupportedOperationException("Unknown relation change type.");
		}

		return null;
	}

	protected class RequestChanges {

		private LinkModification primaryModification;
		private List<LinkModification> secondaryModifications;

		public RequestChanges(LinkModification primaryModification, List<LinkModification> secondaryModifications) {
			this.setPrimaryModification(primaryModification);
			this.setSecondaryModifications(secondaryModifications);
		}

		public LinkModification getPrimaryModification() {
			return this.primaryModification;
		}

		public void setPrimaryModification(LinkModification primaryModification) {
			if (primaryModification == null) {
				throw new IllegalArgumentException("primaryModification cannot be null.");
			}

			this.primaryModification = primaryModification;
		}

		public List<LinkModification> getSecondaryModifications() {
			return this.secondaryModifications;
		}

		public void setSecondaryModifications(List<LinkModification> secondaryModifications) {
			this.secondaryModifications = secondaryModifications;
		}

	}

	protected LinkInfo getLinkInfoForRequest(LinkModificationSystemRequest request)
	        throws UnavailableLinkModelException,
	            UnavailableLinkException {

		// Assert Link Type Exists
		String modelType = request.getLinkModelType();
		LinkModelInfo linkModelInfo = LinkModificationSystemImpl.this.linkSystem.loadLinkModelInfo(modelType);

		// Assert Link Exists
		String linkName = request.getLinkName();
		LinkInfo linkInfo = linkModelInfo.getLinkInfo(linkName);

		return linkInfo;
	}

	// MARK: Runner
	protected class LinkModificationSystemChangesRunner {

		private List<RequestChanges> requestChanges;
		private List<LinkModification> queue = new ArrayList<LinkModification>();

		public LinkModificationSystemChangesRunner(List<RequestChanges> requestChanges) {
			this.requestChanges = requestChanges;
		}

		public void run() {

		}

	}

	protected List<LinkModification> generateRequests() {
		// TODO Auto-generated method stub
		return null;
	}

}
