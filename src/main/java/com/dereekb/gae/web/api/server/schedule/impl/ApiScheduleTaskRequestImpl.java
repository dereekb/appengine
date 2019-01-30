package com.dereekb.gae.web.api.server.schedule.impl;

import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ApiScheduleTaskRequest} implementation.
 * <p>
 * {{@link #getData()} is a {@link JsonNode}.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiScheduleTaskRequestImpl extends AbstractApiScheduleTaskRequestImpl {

	private JsonNode data;

	public ApiScheduleTaskRequestImpl() {
		super();
	}

	public ApiScheduleTaskRequestImpl(String taskEntryName) {
		super(taskEntryName);
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
		return "ApiScheduleTaskRequestImpl [task=" + this.getTask() + ", encodedHeaders=" + this.getEncodedHeaders()
		        + ", encodedParameters=" + this.getEncodedParameters() + ", data=" + this.data + "]";
	}

}
