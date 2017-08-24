package com.dereekb.gae.client.api.model.shared.utility;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility used for mapping client responses.
 * 
 * @author dereekb
 *
 */
public class ClientResponseMapperUtility {

	public ObjectMapper mapper;

	public ClientResponseMapperUtility(ObjectMapper mapper) throws IllegalArgumentException {
		this.setMapper(mapper);
	}

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
	public <X> Set<X> safeMapArrayToSet(JsonNode jsonNode,
	                                    Class<X[]> type)
	        throws ClientResponseSerializationException {
		List<X> list = this.safeMapArrayToList(jsonNode, type);
		return new HashSet<X>(list);
	}

	public <X> List<X> safeMapArrayToList(JsonNode jsonNode,
	                                      Class<X[]> type)
	        throws ClientResponseSerializationException {
		if (jsonNode != null) {
			return this.mapArrayToList(jsonNode, type);
		} else {
			return Collections.emptyList();
		}
	}

	public <X> List<X> mapArrayToList(JsonNode jsonNode,
	                                  Class<X[]> type)
	        throws ClientResponseSerializationException {
		try {
			X[] x = this.mapper.treeToValue(jsonNode, type);
			return ListUtility.toList(x);
		} catch (JsonProcessingException e) {
			throw new ClientResponseSerializationException(e);
		}
	}

	@Override
	public String toString() {
		return "ClientResponseMapperUtility [mapper=" + this.mapper + "]";
	}

}
