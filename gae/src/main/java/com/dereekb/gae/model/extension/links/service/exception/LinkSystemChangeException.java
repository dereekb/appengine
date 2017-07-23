package com.dereekb.gae.model.extension.links.service.exception;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.exceptions.ApiLinkSystemException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.utilities.web.error.impl.ErrorInfoImpl;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Thrown when a {@link LinkModificationSystemRequest} cannot be completed.
 * 
 * Wraps an internal {@link ApiLinkSystemException} with {@link LinkModificationSystemRequest}
 * data.
 *
 * @author dereekb
 */
public class LinkSystemChangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String LINK_CHANGE_ERROR_CODE = "LINK_CHANGE_ERROR";

	private final LinkModificationSystemRequest change;
	private final ApiLinkSystemException reason;

	public LinkSystemChangeException(LinkModificationSystemRequest change, ApiLinkSystemException reason) {
		this(change, reason, null);
	}

	public LinkSystemChangeException(LinkModificationSystemRequest change, ApiLinkSystemException reason, String message) {
		super(message);
		this.change = change;
		this.reason = reason;
	}

	public LinkModificationSystemRequest getChange() {
		return this.change;
	}

	public ApiLinkSystemException getReason() {
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
	 * {@link ApiResponseError} produced by a {@link LinkSystemChangeException}.
	 * 
	 * @author dereekb
	 *
	 */
	@JsonInclude(Include.NON_EMPTY)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class LinkSystemChangeApiResponseError extends ErrorInfoImpl
	        implements ApiResponseError {

		private String action;
		private String link;
		private String key;
		private Set<String> targetKeys;

		public LinkSystemChangeApiResponseError() {}

		public static LinkSystemChangeApiResponseError make(LinkModificationSystemRequest change,
		                                                    ApiLinkSystemException exceptionReason) {
			LinkSystemChangeApiResponseError error = new LinkSystemChangeApiResponseError();

			MutableLinkChangeType action = change.getLinkChangeType();
			String key = change.getPrimaryKey();
			String link = change.getLinkName();

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
