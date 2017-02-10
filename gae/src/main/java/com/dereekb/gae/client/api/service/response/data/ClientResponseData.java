package com.dereekb.gae.client.api.service.response.data;

import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Typed raw response data.
 * 
 * @author dereekb
 *
 * @see ApiResponseData
 */
public interface ClientResponseData {

	/**
	 * Returns the type of the response data.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getDataType();

	/**
	 * Returns data ready for serialization.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getRawData();

}
