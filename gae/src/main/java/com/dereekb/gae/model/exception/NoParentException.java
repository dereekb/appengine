package com.dereekb.gae.model.exception;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown if a parent is expected but one does not exist for a model.
 *
 * @author dereekb
 *
 */
public class NoParentException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "NO_PARENT";
	public static final String ERROR_TITLE = "No Parent";

	private ModelKey childKey;

	public NoParentException(ModelKey modelKey) {
		this(modelKey, null);
	}

	public NoParentException(ModelKey modelKey, String message) {
		super(message);
		this.setChildKey(modelKey);
	}

	public ModelKey getChildKey() {
		return this.childKey;
	}

	public void setChildKey(ModelKey childKey) {
		this.childKey = childKey;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, this.childKey.toString());
	}

}
