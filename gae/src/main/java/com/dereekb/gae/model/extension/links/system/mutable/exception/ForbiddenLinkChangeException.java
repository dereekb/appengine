package com.dereekb.gae.model.extension.links.system.mutable.exception;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Specific {@link MutableLinkChangeException} for forbidden link changes.
 * <p>
 * This is similar to {@link IllegalLinkChangeException}, except the change is rejected for permission-related reasons. 
 * 
 * @author dereekb
 *
 */
public class ForbiddenLinkChangeException extends MutableLinkChangeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "FORBIDDEN_LINK_CHANGE";
	public static final String ERROR_TITLE = "Forbidden Link Change";

	public ForbiddenLinkChangeException(MutableLinkChange linkChange) {
		super(linkChange);
	}

	public ForbiddenLinkChangeException(MutableLinkChange linkChange, String message) {
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
