package com.dereekb.gae.web.api.model.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;

public interface ApiResponseErrorConvertable {

	public ApiResponseError asResponseError();

}
