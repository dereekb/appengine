package com.dereekb.gae.model.extension.links.system.modification.exception;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;

/**
 * Basic exception for {@link LinkModificationSystemRequest} related exceptions.
 *  
 * @author dereekb
 *
 */
public class LinkModificationSystemRequestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private LinkModificationSystemRequest request;

	public LinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		this(null, request);
	}
	
	public LinkModificationSystemRequestException(String message, LinkModificationSystemRequest request) {
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
