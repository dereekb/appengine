package com.dereekb.gae.model.extension.links.service.exception;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.exception.ApiLinkException;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeException.LinkSystemChangeApiResponseError;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Contains a set of {@link LinkSystemChangeException} instances to be thrown
 * together in this container.
 *
 * @author dereekb
 */
public class LinkSystemChangeSetException extends ApiLinkException {

	public static final String API_ERROR_CODE = "LINK_CHANGE_ERROR_SET";

	private static final long serialVersionUID = 1L;

	private List<LinkSystemChangeException> exceptions;

	private transient List<LinkSystemChange> failedChanges;

	public LinkSystemChangeSetException(List<LinkSystemChangeException> exceptions) {
		this.exceptions = exceptions;
	}

	public List<LinkSystemChangeException> getExceptions() {
		return this.exceptions;
	}

	public void setExceptions(List<LinkSystemChangeException> exceptions) {
		this.exceptions = exceptions;
	}

	// MARK: Help
	public List<LinkSystemChange> getFailedLinkChanges() {
		if (this.failedChanges == null) {
			List<LinkSystemChange> failedChanges = new ArrayList<LinkSystemChange>();

			for (LinkSystemChangeException failure : this.exceptions) {
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

		for (LinkSystemChangeException exception : this.exceptions) {
			data.add(exception.asResponseError());
		}

		error.setData(data);

		return error;
	}

}
