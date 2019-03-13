package com.dereekb.gae.client.api.model.shared.utility;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.utilities.data.ObjectMapperUtility;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility used for mapping client responses.
 *
 * @author dereekb
 *
 */
public class ClientResponseMapperUtility {

	private ObjectMapperUtility utility;

	public ClientResponseMapperUtility(ObjectMapper mapper) throws IllegalArgumentException {
		this.setMapper(mapper);
	}

	public ObjectMapper getMapper() {
		return this.utility.getMapper();
	}

	public void setMapper(ObjectMapper mapper) {
		this.utility = ObjectMapperUtilityBuilderImpl.builder(mapper).make();
	}

	// MARK: Utility
	public <X> Set<X> safeMapArrayToSet(JsonNode jsonNode,
	                                    Class<X> type)
	        throws ClientResponseSerializationException {
		List<X> list = this.safeMapArrayToList(jsonNode, type);
		return new HashSet<X>(list);
	}

	public <X> List<X> safeMapArrayToList(JsonNode jsonNode,
	                                      Class<X> type)
	        throws ClientResponseSerializationException {
		if (jsonNode != null) {
			return this.mapArrayToList(jsonNode, type);
		} else {
			return Collections.emptyList();
		}
	}

	public <X> List<X> mapArrayToList(JsonNode jsonNode,
	                                  Class<X> type)
	        throws ClientResponseSerializationException {
		try {
			return this.utility.mapArrayToList(jsonNode, type);
		} catch (IOException e) {
			throw new ClientResponseSerializationException(e);
		}
	}

	public <X> X safeMap(JsonNode jsonNode,
	                     Class<X> type)
	        throws ClientResponseSerializationException {
		try {
			return this.utility.map(jsonNode, type);
		} catch (IOException e) {
			throw new ClientResponseSerializationException(e);
		}
	}

	@Override
	public String toString() {
		return "ClientResponseMapperUtility [mapper=" + this.getMapper() + "]";
	}

}
