package com.dereekb.gae.model.extension.links.service.exception;

import java.util.Set;

import com.dereekb.gae.model.extension.links.exception.ApiLinkException;
import com.dereekb.gae.model.extension.links.exception.LinkException;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.web.error.impl.ErrorInfoImpl;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Thrown when a {@link LinkSystemChange} cannot be completed.
 * 
 * Wraps an internal {@link ApiLinkException} with {@link LinkSystemChange}
 * data.
 *
 * @author dereekb
 *
 */
public class LinkSystemChangeException extends LinkException {

	private static final long serialVersionUID = 1L;

	public static final String LINK_CHANGE_ERROR_CODE = "LINK_CHANGE_ERROR";

	private final LinkSystemChange change;
	private final ApiLinkException reason;

	public LinkSystemChangeException(LinkSystemChange change, ApiLinkException reason) {
		this(change, reason, null);
	}

	public LinkSystemChangeException(LinkSystemChange change, ApiLinkException reason, String message) {
		super(message);
		this.change = change;
		this.reason = reason;
	}

	public LinkSystemChange getChange() {
		return this.change;
	}

	public ApiLinkException getReason() {
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

		public static LinkSystemChangeApiResponseError make(LinkSystemChange change,
		                                                    ApiLinkException exceptionReason) {
			LinkSystemChangeApiResponseError error = new LinkSystemChangeApiResponseError();

			LinkChangeAction action = change.getAction();
			ModelKey key = change.getPrimaryKey();
			String link = change.getLinkName();

			error.setAction(action.getActionName());
			error.setLink(link);
			error.setKey(key.toString());
			error.setTargetKeys(change.getTargetStringKeys());

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
