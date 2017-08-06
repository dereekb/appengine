package com.dereekb.gae.model.extension.links.service.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.exception.failure.LinkModificationSystemRequestFailure;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.web.error.impl.ErrorInfoImpl;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Thrown when a {@link LinkModificationSystemRequest} cannot be completed.
 * 
 * Wraps an internal {@link ApiResponseErrorConvertable} with
 * {@link LinkModificationSystemRequest}
 * data.
 *
 * @author dereekb
 */
public class LinkServiceChangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String LINK_CHANGE_ERROR_CODE = "LINK_CHANGE_ERROR";

	private final LinkModificationSystemRequest change;
	private final ApiResponseErrorConvertable reason;

	public LinkServiceChangeException(LinkModificationSystemRequest change, ApiResponseErrorConvertable reason) {
		this(change, reason, null);
	}

	public LinkServiceChangeException(LinkModificationSystemRequest change,
	        ApiResponseErrorConvertable reason,
	        String message) {
		super(message);
		this.change = change;
		this.reason = reason;
	}

	public static List<LinkServiceChangeException> makeList(List<? extends LinkModificationSystemRequestFailure> failures) {
		List<LinkServiceChangeException> exceptions = new ArrayList<LinkServiceChangeException>();

		for (LinkModificationSystemRequestFailure failure : failures) {
			LinkServiceChangeException exception = make(failure);
			exceptions.add(exception);
		}

		return exceptions;
	}
	
	public static LinkServiceChangeException make(LinkModificationSystemRequestFailure failure) {
		ApiResponseErrorConvertable reason = failure.getError();
		LinkModificationSystemRequest request = failure.getRequest();

		LinkServiceChangeException exception = new LinkServiceChangeException(request, reason);
		return exception;
	}

	public LinkModificationSystemRequest getChange() {
		return this.change;
	}

	public ApiResponseErrorConvertable getReason() {
		return this.reason;
	}

	// MARK: ApiResponseErrorConvertable
	public LinkSystemChangeApiResponseError asResponseError() {
		return LinkSystemChangeApiResponseError.make(this.change, this.reason);
	}

	@Override
	public String toString() {
		return "LinkSystemChangeException [change=" + this.change + ", reason=" + this.reason + "]";
	}

	/**
	 * {@link ApiResponseError} produced by a
	 * {@link LinkServiceChangeException}.
	 * 
	 * @author dereekb
	 *
	 */
	@JsonInclude(Include.NON_EMPTY)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class LinkSystemChangeApiResponseError extends ErrorInfoImpl
	        implements ApiResponseError {

		private String id;
		private String action;
		private String link;
		private String key;
		private Set<String> targetKeys;

		public LinkSystemChangeApiResponseError() {}

		public static LinkSystemChangeApiResponseError make(LinkModificationSystemRequest change,
		                                                    ApiResponseErrorConvertable exceptionReason) {
			LinkSystemChangeApiResponseError error = new LinkSystemChangeApiResponseError();

			MutableLinkChangeType action = change.getLinkChangeType();
			String id = ModelKey.readStringKey(change.keyValue());
			String key = change.getPrimaryKey();
			String link = change.getLinkName();

			error.setId(id);
			error.setAction(action.getActionName());
			error.setLink(link);
			error.setKey(key.toString());
			error.setTargetKeys(change.getKeys());

			ApiResponseError reason = exceptionReason.asResponseError();

			error.setTitle(reason.getErrorTitle());
			error.setCode(reason.getErrorCode());
			error.setDetail(reason.getErrorDetail());

			return error;
		}

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAction() {
			return this.action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getLink() {
			return this.link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Set<String> getTargetKeys() {
			return this.targetKeys;
		}

		public void setTargetKeys(Set<String> targetKeys) {
			this.targetKeys = targetKeys;
		}

		@Override
		public Object getErrorData() {
			return null;
		}

	}

}
