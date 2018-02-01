package com.dereekb.gae.web.api.server.schedule.impl;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ApiScheduleTaskRequest} implementation.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiScheduleTaskRequestImpl
        implements ApiScheduleTaskRequest {

	@NotNull
	@NotEmpty
	private String task;
	private Map<String, String> encodedHeaders;
	private Map<String, String> encodedParameters;
	private JsonNode data;

	public ApiScheduleTaskRequestImpl() {}

	public ApiScheduleTaskRequestImpl(String taskEntryName) {
		this.setTask(taskEntryName);
	}

	@Override
	public String getTask() {
		return this.task;
	}

	public void setTask(String taskEntryName) {
		this.task = taskEntryName;
	}

	@Override
	public Map<String, String> getEncodedHeaders() {
		return this.encodedHeaders;
	}

	public void setEncodedHeaders(Map<String, String> encodedHeaders) {
		this.encodedHeaders = encodedHeaders;
	}

	@Override
	public Map<String, String> getEncodedParameters() {
		return this.encodedParameters;
	}

	public void setEncodedParameters(Map<String, String> encodedParameters) {
		this.encodedParameters = encodedParameters;
	}

	@Override
	public JsonNode getData() {
		return this.data;
	}

	public void setData(JsonNode data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ApiScheduleTaskRequestImpl [task=" + this.task + ", encodedHeaders=" + this.encodedHeaders
		        + ", encodedParameters=" + this.encodedParameters + ", data=" + this.data + "]";
	}

}
