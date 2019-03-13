package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;

/**
 * {@link LinkModificationPair} failure.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationPairFailure {

	/**
	 * Returns the failure reason.
	 * 
	 * @return {@link LinkModificationPairFailureReason}.
	 */
	public LinkModificationPairFailureReason getReason();

	/**
	 * Returns the error if {@link #getReason()} returns
	 * {@link LinkModificationPairFailureReason#ERROR}.
	 * 
	 * @return {@link ApiResponseErrorConvertable}. Can be {@code null}.
	 */
	public ApiResponseErrorConvertable getError();

}
