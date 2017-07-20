package com.dereekb.gae.model.extension.links.system.modification.exception;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;

/**
 * {@link LinkModificationSystemRequestException} thrown by {@link LinkModificationSystemInterface}
 * for invalid requests.
 * 
 * @author dereekb
 *
 */
public class InvalidLinkModificationSystemRequestException extends LinkModificationSystemRequestException {

	private static final long serialVersionUID = 1L;

	public InvalidLinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		super(request);
	}

	public InvalidLinkModificationSystemRequestException(String message, LinkModificationSystemRequest request) {
		super(message, request);
	}

}
