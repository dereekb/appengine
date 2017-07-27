package com.dereekb.gae.web.api.exception.utility;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

public class ApiResponseErrorConvertableUtility {
	
	public static List<ApiResponseError> asResponseErrors(List<? extends ApiResponseErrorConvertable> convertables) {
		List<ApiResponseError> errors = new ArrayList<ApiResponseError>();
		
		for (ApiResponseErrorConvertable convertable : convertables) {
			ApiResponseError error = convertable.asResponseError();
			errors.add(error);
		}
		
		return errors;
	}

}
