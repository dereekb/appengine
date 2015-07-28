package com.dereekb.gae.web.api.model.extension.search.impl;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypeException;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchDelegate;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchUpdateRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * {@link ApiSearchDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class ApiSearchDelegateImpl
        implements ApiSearchDelegate {

	// MARK: ApiSearchDelegate
	@Override
	public ApiResponse searchSingle(String type,
	                                ApiSearchReadRequest request) throws UnavailableTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse searchMultiple(ApiSearchReadRequest request) throws UnavailableTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse querySingle(String type,
	                               ApiSearchReadRequest request) throws UnavailableTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse updateSearchIndex(ApiSearchUpdateRequest request) throws UnavailableTypeException {
		// TODO Auto-generated method stub
		return null;
	}

}
