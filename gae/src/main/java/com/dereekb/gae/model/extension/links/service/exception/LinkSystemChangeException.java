package com.dereekb.gae.model.extension.links.service.exception;

import com.dereekb.gae.model.extension.links.exception.ApiLinkException;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when a {@link LinkSystemChange} cannot be completed.
 *
 * @author dereekb
 *
 */
public class LinkSystemChangeException extends ApiLinkException {

	private static final long serialVersionUID = 1L;

	private final LinkSystemChange change;
	private final ApiLinkException reason;

	public LinkSystemChangeException(LinkSystemChange change, ApiLinkException reason) {
		this(change, reason, null);
	}

	public LinkSystemChangeException(LinkSystemChange change, ApiLinkException reason, String message) {
		super(message);
		this.reason = reason;
		this.change = change;
	}

	public LinkSystemChange getChange() {
		return this.change;
	}

	public ApiLinkException getReason() {
		return this.reason;
	}

	@Override
	public ApiResponseError getResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl();

		ModelKey primaryKey = this.change.getPrimaryKey();
		LinkChangeAction action = this.change.getAction();
		String link = this.change.getLinkName();

		ApiResponseError reasonError = this.reason.getResponseError();
		ErrorData data = new ErrorData(action, link, primaryKey, reasonError);

		error.setData(data);

		return error;
	}

	@Override
	public String toString() {
		return "LinkSystemChangeException [change=" + this.change + ", reason=" + this.reason + "]";
	}

	public static class ErrorData {

		private String action;
		private String link;
		private String key;

		private ApiResponseError reason;

		public ErrorData(LinkChangeAction action, String link, ModelKey key, ApiResponseError reason) {
			this.action = action.getAction();
			this.link = link;
			this.key = key.toString();
			this.reason = reason;
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

		public ApiResponseError getReason() {
			return this.reason;
		}

		public void setReason(ApiResponseError reason) {
			this.reason = reason;
		}

	}

}
