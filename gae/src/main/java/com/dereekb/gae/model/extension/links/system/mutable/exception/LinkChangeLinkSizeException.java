package com.dereekb.gae.model.extension.links.system.mutable.exception;

import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Specific {@link MutableLinkChangeException} for links with size {@link LinkSize#ONE} that are modified with more than one key.
 * 
 * @author dereekb
 *
 */
public class LinkChangeLinkSizeException extends MutableLinkChangeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "LINK_SIZE_FAILURE";
	public static final String ERROR_TITLE = "Link Change Size Failure";

	public LinkChangeLinkSizeException(MutableLinkChange linkChange) {
		super(linkChange);
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		error.setDetail(this.getMessage());
		return error;
	}

	@Override
	public String toString() {
		return "LinkChangeLinkSizeException [getLinkChange()=" + this.getLinkChange() + ", getMessage()=" + this.getMessage() + "]";
	}

}
