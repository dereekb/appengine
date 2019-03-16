package com.dereekb.gae.model.extension.links.system.modification.exception.request;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.exception.LinkChangeLinkSizeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * Thrown when too many keys are provided by a request. Similar to {@link LinkChangeLinkSizeException}.
 * 
 * @author dereekb
 *
 */
public class TooManyChangeKeysException extends InvalidLinkModificationSystemRequestException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "TOO_MANY_KEYS";
	public static final String ERROR_TITLE = "Too Many Keys";

	public TooManyChangeKeysException(LinkModificationSystemRequest request) {
		this(request, "Too many keys for this size.");
	}

	public TooManyChangeKeysException(LinkModificationSystemRequest request, String message) {
		super(request, message);
	}

	// MARK: ApiLinkSystemException
	@Override
	public ApiResponseError asResponseError() {
		return super.makeResponseError(ERROR_CODE, ERROR_TITLE);
	}
	
}
