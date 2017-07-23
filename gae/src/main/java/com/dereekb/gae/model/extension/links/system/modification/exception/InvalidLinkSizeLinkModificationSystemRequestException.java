package com.dereekb.gae.model.extension.links.system.modification.exception;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.exception.LinkChangeLinkSizeException;

/**
 * {@link InvalidLinkModificationSystemRequestException} extension that is similar to {@link LinkChangeLinkSizeException}.
 * 
 * @author dereekb
 *
 */
public class InvalidLinkSizeLinkModificationSystemRequestException extends InvalidLinkModificationSystemRequestException {

	private static final long serialVersionUID = 1L;

	public InvalidLinkSizeLinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		super(request, "Invalid link size.");
	}

	public InvalidLinkSizeLinkModificationSystemRequestException(LinkModificationSystemRequest request,
	        String message) {
		super(request, message);
	}

}
