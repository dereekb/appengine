package com.dereekb.gae.model.extension.links.system.modification.exception;

import com.dereekb.gae.model.extension.links.system.components.exceptions.ApiLinkSystemException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;

/**
 * Abstract exception for {@link LinkModificationSystemRequest} related exceptions.
 *  
 * @author dereekb
 *
 */
public abstract class LinkModificationSystemRequestException extends ApiLinkSystemException {

	private static final long serialVersionUID = 1L;
	
	private LinkModificationSystemRequest request;

	public LinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		this(request, null);
	}
	
	public LinkModificationSystemRequestException(LinkModificationSystemRequest request, String message) {
		super(message);
		this.setRequest(request);
	}
	
	public LinkModificationSystemRequest getRequest() {
		return this.request;
	}

	public void setRequest(LinkModificationSystemRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("request cannot be null.");
		}
	
		this.request = request;
	}

}
