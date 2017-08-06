package com.dereekb.gae.model.extension.links.service.exception;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeException.LinkSystemChangeApiResponseError;
import com.dereekb.gae.model.extension.links.system.components.exceptions.ApiLinkSystemException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.exception.failure.LinkModificationFailedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.failure.LinkModificationSystemRequestFailure;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Contains a set of {@link LinkServiceChangeException} instances to be thrown
 * together in this container.
 *
 * @author dereekb
 */
public class LinkServiceChangeSetException extends ApiLinkSystemException {

	public static final String API_ERROR_CODE = "LINK_CHANGE_ERROR_SET";

	private static final long serialVersionUID = 1L;

	private List<LinkServiceChangeException> exceptions;

	private transient List<LinkModificationSystemRequest> failedChanges;

	public LinkServiceChangeSetException(List<LinkServiceChangeException> exceptions) {
		this.exceptions = exceptions;
	}

	public static LinkServiceChangeSetException make(LinkModificationFailedException e) {
		List<? extends LinkModificationSystemRequestFailure> failures = e.getRequestFailures();
		List<LinkServiceChangeException> exceptions = LinkServiceChangeException.makeList(failures);
		return new LinkServiceChangeSetException(exceptions);
	}

	public List<LinkServiceChangeException> getExceptions() {
		return this.exceptions;
	}

	public void setExceptions(List<LinkServiceChangeException> exceptions) {
		this.exceptions = exceptions;
	}

	// MARK: Help
	public boolean hasErrors() {
		return this.exceptions.size() > 0;
	}

	public List<LinkModificationSystemRequest> getFailedLinkChanges() {
		if (this.failedChanges == null) {
			List<LinkModificationSystemRequest> failedChanges = new ArrayList<LinkModificationSystemRequest>();

			for (LinkServiceChangeException failure : this.exceptions) {
				failedChanges.add(failure.getChange());
			}

			this.failedChanges = failedChanges;
		}

		return this.failedChanges;
	}

	// MARK: ApiLinkException
	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(API_ERROR_CODE);
		error.setTitle("Link Change Errors");
		error.setDetail("One or more errors occured while performing link changes.");

		List<LinkSystemChangeApiResponseError> data = new ArrayList<LinkSystemChangeApiResponseError>();

		for (LinkServiceChangeException exception : this.exceptions) {
			data.add(exception.asResponseError());
		}

		error.setData(data);

		return error;
	}

}
