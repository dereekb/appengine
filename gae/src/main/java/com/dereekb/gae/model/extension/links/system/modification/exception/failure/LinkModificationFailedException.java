package com.dereekb.gae.model.extension.links.system.modification.exception.failure;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.components.exceptions.ApiLinkSystemException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationSystemUtility;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.KeyedApiResponseError;

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

	public LinkModificationFailedException(List<? extends LinkModificationSystemRequestFailure> requestFailures) {
		this(requestFailures, "One or more requests resulted in a failure.");
	}

	public LinkModificationFailedException(List<? extends LinkModificationSystemRequestFailure> requestFailures,
	        String message) {
		super(message);
		this.setRequestFailures(requestFailures);
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
		ApiResponseErrorImpl responseError = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);

		List<KeyedApiResponseError> keyedErrors = new ArrayList<KeyedApiResponseError>();

		for (LinkModificationSystemRequestFailure failure : this.requestFailures) {
			ApiResponseErrorConvertable reason = failure.getError();
			ApiResponseError error = reason.asResponseError();

			LinkModificationSystemRequest request = failure.getRequest();
			ModelKey key = request.keyValue();

			KeyedApiResponseError keyedError = new KeyedApiResponseError(key, error);
			keyedErrors.add(keyedError);
		}

		responseError.setData(keyedErrors);

		return responseError;
	}

	@Override
	public String toString() {
		return "LinkModificationFailedException [requestFailures=" + this.requestFailures + "]";
	}

}
