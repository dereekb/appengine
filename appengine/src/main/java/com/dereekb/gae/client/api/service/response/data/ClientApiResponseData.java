package com.dereekb.gae.client.api.service.response.data;

import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Typed raw response data that is {@link AlwaysKeyed} by it's data type.
 *
 * @author dereekb
 *
 * @see ApiResponseData
 * @see ApiResponseImpl
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
	 * Returns the JSON node for the response data.
	 *
	 * @return {@link JsonNode}. Never {@code null}.
	 */
	public JsonNode getJsonNode();

	/**
	 * Returns the JSON node for the data to be serialized.
	 *
	 * @return {@link JsonNode}. Never {@code null}.
	 * @deprecated {@link #getJsonNode()} returns this data now.
	 */
	@Deprecated
	public JsonNode getDataJsonNode();

}
