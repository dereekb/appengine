package com.dereekb.gae.model.extension.links.system.mutable.exception;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Specific {@link IllegalLinkChangeException} for read-only links.
 * 
 * @author dereekb
 *
 */
public class ReadOnlyLinkChangeException extends IllegalLinkChangeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "READ_ONLY_LINK_CHANGE";
	public static final String ERROR_TITLE = "Illegal Read Only Link Change";

	public ReadOnlyLinkChangeException(MutableLinkChange linkChange) {
		super(linkChange);
	}

	public ReadOnlyLinkChangeException(MutableLinkChange linkChange, String message) {
		super(linkChange, message);
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
		return "IllegalLinkChangeException [getLinkChange()=" + this.getLinkChange() + ", getMessage()=" + this.getMessage() + "]";
	}

}
