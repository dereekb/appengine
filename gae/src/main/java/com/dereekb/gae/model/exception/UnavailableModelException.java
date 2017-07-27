package com.dereekb.gae.model.exception;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception for {@link ModelKey} based requests that require a model that is
 * unavailable.
 * <p>
 * Might sometimes not include any models keys.
 *
 * @author dereekb
 *
 */
public class UnavailableModelException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "UNAVAILABLE_MODEL";
	public static final String ERROR_TITLE = "Unavailable Models";

	private final Set<ModelKey> modelKeys;

	public UnavailableModelException(String message) {
		this((Collection<ModelKey>) null, message);
	}
	
	public UnavailableModelException(ModelKey modelKey) {
		this(modelKey, null);
	}
	
	public UnavailableModelException(Collection<ModelKey> modelKeys) {
		this(modelKeys, null);
	}

	public UnavailableModelException(ModelKey modelKey, String message) {
		this(SetUtility.wrap(modelKey), message);
	}

	public UnavailableModelException(Collection<ModelKey> modelKeys, String message) {
		super(message);
		
		if (modelKeys != null) {
			this.modelKeys = new HashSet<ModelKey>(modelKeys);
		} else {
			this.modelKeys = Collections.emptySet();
		}
		
	}
	
	public Set<ModelKey> getModelKeys() {
		return this.modelKeys;
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseError asResponseError() {
		List<String> keys = ModelKey.readStringKeys(this.modelKeys);
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, keys);
	}

	@Override
	public String toString() {
		return "UnavailableModelException [modelKeys=" + this.modelKeys + "]";
	}

}
