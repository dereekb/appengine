package com.dereekb.gae.web.api.model.extension.link.impl;

import java.util.List;

import com.dereekb.gae.web.api.model.extension.link.LinkExtensionApiController;
import com.dereekb.gae.web.api.shared.request.AtomicApiRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API Request for link changes.
 *
 * @author dereekb
 * @see {@link LinkExtensionApiController}
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiLinkChangeRequest extends AtomicApiRequest<ApiLinkChangeImpl> {

	public ApiLinkChangeRequest() {
		super();
	};

	public ApiLinkChangeRequest(List<ApiLinkChangeImpl> data) {
		super(data);
	}

	public ApiLinkChangeRequest(List<ApiLinkChangeImpl> data, boolean atomic) {
		this.setData(data);
		this.setAtomic(atomic);
	}

	@Override
	public String toString() {
		return "ApiLinkChangeRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
