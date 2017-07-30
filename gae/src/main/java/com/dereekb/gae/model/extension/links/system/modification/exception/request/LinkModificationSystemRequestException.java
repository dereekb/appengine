package com.dereekb.gae.model.extension.links.system.modification.exception.request;

import com.dereekb.gae.model.extension.links.system.components.exceptions.ApiLinkSystemException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Abstract exception for {@link LinkModificationSystemRequest} related exceptions.
 *  
 * @author dereekb
 *
 */
public abstract class LinkModificationSystemRequestException extends ApiLinkSystemException {

	private static final long serialVersionUID = 1L;
	
	private LinkModificationSystemRequest request;

	public LinkModificationSystemRequestException(LinkModificationSystemRequest request) {
		this(request, null);
	}
	
	public LinkModificationSystemRequestException(LinkModificationSystemRequest request, String message) {
		super(message);
		this.setRequest(request);
	}
	
	public LinkModificationSystemRequest getRequest() {
		return this.request;
	}

	public void setRequest(LinkModificationSystemRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("request cannot be null.");
		}
	
		this.request = request;
	}
	
	// MARK: API Errors
	public ApiResponseError makeResponseError(String errorCode,
	                                          String errorTitle) {
		LinkModificationSystemRequestExceptionInfo info = this.makeRequestExceptionInfo();
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(errorCode, errorTitle, info);
		return error;
	}
	
	public LinkModificationSystemRequestExceptionInfo makeRequestExceptionInfo() {
		return new LinkModificationSystemRequestExceptionInfo(this.request, this.getMessage());
	}

}
