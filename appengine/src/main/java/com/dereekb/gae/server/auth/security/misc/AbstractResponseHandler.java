package com.dereekb.gae.server.auth.security.misc;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class that provides access to a {@link ObjectMapper} and functions
 * for writing responses.
 *
 * @author dereekb
 *
 */
public abstract class AbstractResponseHandler {

	private static final ObjectMapper DEFAULT_MAPPER = ObjectMapperUtilityBuilderImpl.MAPPER;

	private ObjectMapper mapper = DEFAULT_MAPPER;

	public ObjectMapper getMapper() {
		return this.mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		if (mapper == null) {
			throw new IllegalArgumentException("mapper cannot be null.");
		}

		this.mapper = mapper;
	}

	// MARK: Utility
	protected void writeJsonResponse(HttpServletResponse response,
	                                 Object object)
	        throws JsonGenerationException,
	            JsonMappingException,
	            IOException {

		response.setContentType("application/json");

		ServletOutputStream outputStream = response.getOutputStream();
		this.mapper.writeValue(outputStream, object);
	}

}
