package com.dereekb.gae.model.extension.links.system.modification.exception;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * {@link LinkModificationSystemRequestException} thrown by {@link LinkModificationSystemInterface}
 * for invalid requests.
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

	@Override
	public ApiResponseError asResponseError() {
		// TODO Auto-generated method stub
		return null;
	}

}
