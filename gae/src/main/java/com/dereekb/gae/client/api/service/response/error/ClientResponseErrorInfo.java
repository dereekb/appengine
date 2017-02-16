package com.dereekb.gae.client.api.service.response.error;

import com.dereekb.gae.utilities.web.error.ErrorInfo;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Error information for unsuccessful client responses.
 *
 * @author dereekb
 * 
 * @see ApiResponseError
 */
public interface ClientResponseErrorInfo
        extends ErrorInfo {

	/**
	 * Returns a json node for the data.
	 * 
	 * @return {@link JsonNode}.
	 */
	public JsonNode getErrorData();

}