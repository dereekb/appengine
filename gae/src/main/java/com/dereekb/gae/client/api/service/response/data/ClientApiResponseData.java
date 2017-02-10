package com.dereekb.gae.client.api.service.response.data;

import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Typed raw response data that is {@link AlwaysKeyed} by it's data type.
 * 
 * @author dereekb
 *
 * @see ApiResponseData
 */
public interface ClientApiResponseData
        extends AlwaysKeyed<String> {

	/**
	 * Returns the type of the response data.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getDataType();

	/**
	 * Returns the JSON node for the data to be serialized.
	 *
	 * @return {@link JsonNode}. Never {@code null}.
	 */
	public JsonNode getDataJsonNode();

}
