package com.dereekb.gae.model.extension.links.system.modification.exception.failure;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequestReference;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;

/**
 * {@link LinkModificationSystemRequestReference} that wraps an {@link ApiResponseErrorConvertable} and is used to track errors.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemRequestFailure extends LinkModificationSystemRequestReference {
	
	/**
	 * Returns the error that caused the failure.
	 * 
	 * @return {@link ApiResponseErrorConvertable}. Can be {@code null}.
	 */
	public ApiResponseErrorConvertable getError();

}
