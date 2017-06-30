package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;

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

	protected class LinkModificationSystemInstanceImpl
	        implements LinkModificationSystemInstance {

		@Override
		public void addRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkException,
		            InvalidLinkModificationSystemRequestException {
			// TODO Auto-generated method stub

		}

		private validateRequest(LinkModificationSystemRequest request) {
			String modelType = request.getLinkModelType();
			
			
			try {
				LinkModificationSystemImpl.this.linkSystem.loadLinkModelInfo(modelType);
			} catch (UnavailableLinkModelException e) {
				
			}
			
			
			
		}

	}

}
