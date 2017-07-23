package com.dereekb.gae.model.extension.links.system.modification.exception;

import com.dereekb.gae.model.extension.links.system.components.exceptions.ApiLinkSystemException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * 
 * @author dereekb
 *
 */
public class MissingLinkModelsException extends ApiLinkSystemException {
	
	@Override
	public ApiResponseError asResponseError() {
		// TODO Auto-generated method stub
		return null;
	}

}
