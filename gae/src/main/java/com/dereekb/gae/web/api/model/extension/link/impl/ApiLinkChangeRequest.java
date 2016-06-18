package com.dereekb.gae.web.api.model.extension.link.impl;

import java.util.List;

import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;
import com.dereekb.gae.web.api.model.extension.link.LinkExtensionApiController;
import com.dereekb.gae.web.api.shared.request.ApiRequest;

/**
 * API Request for link changes.
 *
 * @author dereekb
 * @see {@link LinkExtensionApiController}
 */
public class ApiLinkChangeRequest extends ApiRequest<ApiLinkChange> {

	private boolean atomic = true;

	public ApiLinkChangeRequest(List<ApiLinkChange> data) {
		super(data);
	}

	public ApiLinkChangeRequest(List<ApiLinkChange> data, boolean atomic) {
		this.setData(data);
		this.atomic = atomic;
	}

	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
	public String toString() {
		return "ApiLinkChangeRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
