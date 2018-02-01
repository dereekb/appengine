package com.dereekb.gae.web.api.server.schedule.impl;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ApiScheduleTaskRequest} implementation.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractApiScheduleTaskRequestImpl
        implements ApiScheduleTaskRequest {

	@NotNull
	@NotEmpty
	private String task;
	private Map<String, String> encodedHeaders;
	private Map<String, String> encodedParameters;

	public AbstractApiScheduleTaskRequestImpl() {}

	public AbstractApiScheduleTaskRequestImpl(String taskEntryName) {
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

}
