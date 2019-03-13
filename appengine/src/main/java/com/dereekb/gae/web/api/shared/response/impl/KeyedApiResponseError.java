package com.dereekb.gae.web.api.shared.response.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Wraps an {@link ApiResponseError} and string model key together.
 * <p>
 * The key is optional.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyedApiResponseError {

	private String key;
	private ApiResponseErrorImpl error;

	public KeyedApiResponseError() {};

	public KeyedApiResponseError(ApiResponseError error) {
		this((String) null, error);
	};
	
	public KeyedApiResponseError(ModelKey key, ApiResponseError error) {
		this(ModelKey.readStringKey(key), error);
	}

	public KeyedApiResponseError(String key, ApiResponseError error) {
		super();
		this.setKey(key);
		this.setError(error);
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ApiResponseErrorImpl getError() {
		return this.error;
	}

	public void setError(ApiResponseError error) {
		if (error == null) {
			throw new IllegalArgumentException("error cannot be null.");
		}

		this.error = ApiResponseErrorImpl.asErrorImpl(error);
	}

	@Override
	public String toString() {
		return "KeyedApiResponseErrorWrap [key=" + this.key + ", error=" + this.error + "]";
	}

}
