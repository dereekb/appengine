package com.dereekb.gae.web.api.shared.response;

import java.util.List;
import java.util.Map;

/**
 * A web-safe API response.
 * <p>
 * Response implementations should expect to be serialized, so they should not
 * contain any internal types that should not be exposed outside the system.
 *
 * @author dereekb
 *
 */
public interface ApiResponse {

	/**
	 * Whether or not the response was successful.
	 *
	 * @return {@code true} if the request for this response was successful.
	 */
	public boolean getResponseSuccess();

	/**
	 * The primary response data.
	 *
	 * @return primary {@link ApiResponseData} for this response. {@code null}
	 *         if no data.
	 */
	public ApiResponseData getResponsePrimaryData();

	/**
	 * Extra response data that is related to the primary data.
	 *
	 * @return {@link Map} of {@link ApiResponseData} included, or {@code null}
	 *         if not specified.
	 */
	public Map<String, ApiResponseData> getResponseIncludedData();

	/**
	 * Returns any errors encountered.
	 *
	 * @return {@link List} of {@link ApiResponseError} values, or {@code null}
	 *         if none.
	 */
	public List<ApiResponseError> getResponseErrors();

}
