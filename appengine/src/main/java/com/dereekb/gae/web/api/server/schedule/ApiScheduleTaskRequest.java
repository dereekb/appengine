package com.dereekb.gae.web.api.server.schedule;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Request for {@link ApiScheduleTaskController}.
 *
 * @author dereekb
 *
 */
public interface ApiScheduleTaskRequest {

	/**
	 * Returns the task entry name that should correspond to an entry within the
	 * {@link ApiScheduleTaskController}.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getTask();

	/**
	 * @return {@link Map}, or {@code null} if no headers.
	 */
	public Map<String, String> getEncodedHeaders();

	/**
	 * @return {@link Map}, or {@code null} if no parameters.
	 */
	public Map<String, String> getEncodedParameters();

	/**
	 * @return {@link JsonNode} or {@code null} if no data.
	 */
	public JsonNode getData();

}
