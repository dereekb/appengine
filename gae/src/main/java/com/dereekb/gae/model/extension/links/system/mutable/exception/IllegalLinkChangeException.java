package com.dereekb.gae.model.extension.links.system.mutable.exception;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Specific {@link MutableLinkChangeException} for invalid link changes.
 * 
 * @author dereekb
 *
 */
public class IllegalLinkChangeException extends MutableLinkChangeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "ILLEGAL_LINK_CHANGE";
	public static final String ERROR_TITLE = "Illegal Link Change";

	public IllegalLinkChangeException(MutableLinkChange linkChange) {
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
		return "IllegalLinkChangeException [getLinkChange()=" + this.getLinkChange() + ", getMessage()=" + this.getMessage() + "]";
	}

}
