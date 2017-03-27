package com.dereekb.gae.server.datastore.models.keys.exception;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when one or more model keys are not initialized when they are expected
 * to be.
 * <p>
 * For example, a model key with the ID 0 is a valid key in some places (used as
 * an index), but in some cases the id 0 is special and represents an
 * uninitialized/blank value.
 * 
 * @author dereekb
 */
public class UninitializedModelKeyException extends ApiSafeRuntimeException {

	public static final String ERROR_CODE = "UNINITIALIZED_MODEL_KEY";
	public static final String ERROR_TITLE = "Uninitialized Model Key";

	private static final long serialVersionUID = 1L;

	private ModelKey key;

	public ModelKey getKey() {
		return this.key;
	}

	public void setKey(ModelKey key) {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null.");
		}

		this.key = key;
	}

	public UninitializedModelKeyException() {
		super();
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		String detail = this.getMessage();
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, detail);
	}

}
