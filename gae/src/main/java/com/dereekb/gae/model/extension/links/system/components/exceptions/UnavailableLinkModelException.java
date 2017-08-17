package com.dereekb.gae.model.extension.links.system.components.exceptions;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when a requested link model type is unavailable.
 * 
 * @author dereekb
 *
 */
public class UnavailableLinkModelException extends ApiLinkSystemException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "LINK_MODEL_TYPE_UNAVAILABLE";
	public static final String ERROR_TITLE = "Link Model Type Unavailable";

	private String type;

	public static UnavailableLinkModelException makeForType(String type) {
		return new UnavailableLinkModelException(type, "The model type '" + type + "' was unavailable.");
	}
	
	public UnavailableLinkModelException(String type, String message) {
		super(message);
		this.setType(type);
	}	
	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}
	
		this.type = type;
	}

	// MARK: ApiLinkException
	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		
		error.setDetail(this.type);

		return error;
	}

	@Override
	public String toString() {
		return "UnavailableLinkModelException [type=" + this.type + "]";
	}

}
