package com.dereekb.gae.model.extension.links.system.modification.exception.failure;

import java.util.List;

import com.dereekb.gae.model.extension.links.system.components.exceptions.ApiLinkSystemException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationSystemUtility;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception thrown by {@link LinkModificationSystem} when one or more requests
 * fail when atomically configured.
 * 
 * @author dereekb
 *
 */
public class LinkModificationFailedException extends ApiLinkSystemException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "FAILED_MODIFICATIONS";
	public static final String ERROR_TITLE = "Failed Modifications";

	private List<? extends LinkModificationSystemRequestFailure> requestFailures;

	public LinkModificationFailedException(List<? extends LinkModificationSystemRequestFailure> failedRequests) {
		this.setRequestFailures(failedRequests);
	}

	public List<? extends LinkModificationSystemRequestFailure> getRequestFailures() {
		return this.requestFailures;
	}

	public void setRequestFailures(List<? extends LinkModificationSystemRequestFailure> requestFailures) {
		if (requestFailures == null) {
			throw new IllegalArgumentException("requestFailures cannot be null.");
		}

		this.requestFailures = requestFailures;
	}

	public List<LinkModificationSystemRequest> getFailedRequests() {
		return LinkModificationSystemUtility.readRequests(this.requestFailures);
	}
	
	// MARK: ApiLinkSystemException
	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);

		return error;
	}

}
