package com.dereekb.gae.web.api.model.extension.iterate.impl;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskRequest;
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
public class ApiScheduleTaskRequestImpl
        implements ApiScheduleTaskRequest {

	@NotNull
	@NotEmpty
	private String taskEntryName;
	
	private Map<String, String> encodedHeaders;
	private Map<String, String> encodedParameters;

	public ApiScheduleTaskRequestImpl() {}
	
	public ApiScheduleTaskRequestImpl(String taskEntryName) {
		this.setTaskEntryName(taskEntryName);
	}

	@Override
	public String getTaskEntryName() {
		return this.taskEntryName;
	}

	public void setTaskEntryName(String taskEntryName) {
		this.taskEntryName = taskEntryName;
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
	public String toString() {
		return "ApiScheduleTaskRequestImpl [taskEntryName=" + this.taskEntryName + ", encodedHeaders=" + this.encodedHeaders
		        + ", encodedParameters=" + this.encodedParameters + "]";
	}

}
