package com.dereekb.gae.model.extension.links.system.modification.exception.request;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * {@link InvalidLinkModificationSystemRequestException} extension thrown for changes that are illegal for a link.
 * 
 * @author dereekb
 *
 */
public class IllegalLinkChangeLinkModificationSystemRequestException extends InvalidLinkModificationSystemRequestException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "ILLEGAL_LINK_CHANGE";
	public static final String ERROR_TITLE = "Illegal Link Change";

	public IllegalLinkChangeLinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		super(request);
	}

	public IllegalLinkChangeLinkModificationSystemRequestException(LinkModificationSystemRequest request,
	        String message) {
		super(request, message);
	}

	// MARK: ApiLinkSystemException
	@Override
	public ApiResponseError asResponseError() {
		return super.makeResponseError(ERROR_CODE, ERROR_TITLE);
	}
	
}
