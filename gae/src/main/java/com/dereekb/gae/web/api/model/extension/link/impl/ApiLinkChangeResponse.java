package com.dereekb.gae.web.api.model.extension.link.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ApiResponseImpl} that contains missing keys on failures.
 * 
 * @author dereekb
 * 
 * @deprecated Use {@link ApiResponseImpl} instead.
 */
@Deprecated
@JsonInclude(Include.NON_DEFAULT)
public class ApiLinkChangeResponse extends ApiResponseImpl {

	private Map<String, List<String>> missingLinkKeys;

	public ApiLinkChangeResponse() {
		super();
	}

	public ApiLinkChangeResponse(ApiResponseData data) {
		super(data);
	}

	public ApiLinkChangeResponse(boolean success) {
		super(success);
	}

	public Map<String, List<String>> getMissingLinkKeys() {
		return this.missingLinkKeys;
	}

	public void setMissingLinkKeys(Map<String, List<String>> missingLinkKeys) {
		this.missingLinkKeys = missingLinkKeys;
	}

}
