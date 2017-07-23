package com.dereekb.gae.model.extension.links.system.modification.exception;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * Thrown when a single model gets more than one change queue'd for a single
 * link.
 * 
 * @author dereekb
 *
 */
public class ConflictingLinkModificationSystemRequestException extends LinkModificationSystemRequestException {

	private static final long serialVersionUID = 1L;

	public ConflictingLinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		super(request);
	}

	public ConflictingLinkModificationSystemRequestException(LinkModificationSystemRequest request, String message) {
		super(request, message);
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseError asResponseError() {
		
		
		
		return null;
	}
	
}
