package com.dereekb.gae.model.extension.links.system.mutable.exception;

import com.dereekb.gae.model.extension.links.exception.ApiLinkException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * {@link ApiLinkException} thrown if modifying a link fails.
 * 
 * @author dereekb
 *
 */
public class MutableLinkChangeException extends ApiLinkException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "CHANGE_FAILURE";
	public static final String ERROR_TITLE = "Link Change Failure";

	private MutableLinkChange linkChange;

	public MutableLinkChangeException(MutableLinkChange linkChange) {
		super();
		this.setLinkChange(linkChange);
	}

	public MutableLinkChange getLinkChange() {
		return this.linkChange;
	}

	public void setLinkChange(MutableLinkChange linkChange) {
		if (linkChange == null) {
			throw new IllegalArgumentException("linkChange cannot be null.");
		}

		this.linkChange = linkChange;
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
		return "MutableLinkChangeException [linkChange=" + this.linkChange + "]";
	}

}
