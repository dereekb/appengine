package com.dereekb.gae.model.extension.read.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.web.api.model.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception for when a requested types is unavailable.
 *
 * @author dereekb
 */
public class UnavailableTypesException extends RuntimeException
        implements ApiResponseErrorConvertable {

	private static final long serialVersionUID = 1L;

	public static final String API_RESPONSE_ERROR_CODE = "UNAVAILABLE_TYPE";
	public static final String API_RESPONSE_ERROR_TITLE = "Unavailable Type";

	protected final Set<String> types;

	public UnavailableTypesException(String type) {
		this(SingleItem.withValue(type));
	}

	public UnavailableTypesException(Collection<String> types) {
		this.types = new HashSet<String>(types);
	}

	public Set<String> getTypes() {
		return this.types;
	}

	@Override
	public ApiResponseError asResponseError() {
		return makeApiError(this.types, this.getMessage());
	}

	public static ApiResponseErrorImpl makeApiError(Set<String> types,
	                                                String message) {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(API_RESPONSE_ERROR_CODE);
		error.setTitle(API_RESPONSE_ERROR_TITLE);
		error.setDetail(message);
		error.setData(types);
		return error;
	}

	@Override
	public String toString() {
		return "UnavailableTypesException [types=" + this.types + "]";
	}

}
