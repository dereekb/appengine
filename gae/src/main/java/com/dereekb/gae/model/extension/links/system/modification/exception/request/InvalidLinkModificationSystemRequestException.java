package com.dereekb.gae.model.extension.links.system.modification.exception.request;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * {@link LinkModificationSystemRequestException} thrown by {@link LinkModificationSystemInterface}
 * for invalid requests (requests that can be trivially checked for validity).
 * 
 * @author dereekb
 *
 */
public class InvalidLinkModificationSystemRequestException extends LinkModificationSystemRequestException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "INVALID_LINK_MODIFICATION";
	public static final String ERROR_TITLE = "Invalid Link Modification";

	public InvalidLinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		super(request);
	}

	public InvalidLinkModificationSystemRequestException(LinkModificationSystemRequest request, String message) {
		super(request, message);
	}

	// MARK: ApiLinkSystemException
	@Override
	public ApiResponseError asResponseError() {
		return super.makeResponseError(ERROR_CODE, ERROR_TITLE);
	}

}
