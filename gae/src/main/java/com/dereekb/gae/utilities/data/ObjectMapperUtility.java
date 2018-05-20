package com.dereekb.gae.utilities.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ObjectMapper} utility.
 *
 * @author dereekb
 *
 */
public interface ObjectMapperUtility {

	// Accessors
	/**
	 *
	 * @return {@link ObjectMapper}. Never {@code null}.
	 */
	public ObjectMapper getMapper();

	/**
	 * If {@code true}, all functions will catch null values and return null or
	 * an empty {@link Collection}.
	 *
	 * @return {@code true} if {@code null} safe.
	 */
	public boolean isNullSafe();

	// Safe Utilities (No Exception)
	public <X> Set<X> safeMapArrayToSet(JsonNode jsonNode,
	                                    Class<X> type);

	public <X> List<X> safeMapArrayToList(JsonNode jsonNode,
	                                      Class<X> type);

	public <X> X safeMap(JsonNode jsonNode,
	                     Class<X> type);

	// Utilities
	public <X> Set<X> mapArrayToSet(JsonNode jsonNode,
	                                Class<X> type)
	        throws IOException;

	public <X> List<X> mapArrayToList(JsonNode jsonNode,
	                                  Class<X> type)
	        throws IOException;

	public <X> X map(String json,
	                 Class<X> type)
	        throws IOException;

	public <X> X map(JsonNode jsonNode,
	                 Class<X> type)
	        throws IOException;

	public Map<String, String> mapToStringMap(JsonNode jsonNode);

	// Writer
	public String writeJsonString(JsonNode jsonNode);

}
