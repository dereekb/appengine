package com.dereekb.gae.model.extension.links.system.modification.exception.request;

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

	public static final String ERROR_CODE = "CONFLICTING_LINK_MODIFICATION";
	public static final String ERROR_TITLE = "Conflicting Link Modification";

	public ConflictingLinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		super(request);
	}
 
	public ConflictingLinkModificationSystemRequestException(LinkModificationSystemRequest request, String message) {
		super(request, message);
	}

	// MARK: ApiLinkSystemException
	@Override
	public ApiResponseError asResponseError() {
		return super.makeResponseError(ERROR_CODE, ERROR_TITLE);
	}
	
}
