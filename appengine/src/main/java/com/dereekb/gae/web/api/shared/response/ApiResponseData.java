package com.dereekb.gae.web.api.shared.response;

/**
 * Data that is returned inside an {@link ApiResponse}.
 * <p>
 * All data will potentially reach outside sources, so implementations should
 * keep this in mind.
 * </p>
 *
 * @author dereekb
 *
 */
public interface ApiResponseData {

	/**
	 * Returns the type of the response data. The type is case-insensitive.
	 *
	 * @return {@link String} of the response data's type. Never {@code null}.
	 */
	public String getResponseDataType();

	/**
	 * Returns API response data ready for serialization.
	 *
	 * @return {@link Object} of the response data. Never {@code null}.
	 */
	public Object getResponseData();

}
