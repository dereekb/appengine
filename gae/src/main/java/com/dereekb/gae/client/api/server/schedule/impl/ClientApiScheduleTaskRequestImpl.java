package com.dereekb.gae.client.api.server.schedule.impl;

import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.server.schedule.impl.AbstractApiScheduleTaskRequestImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ApiScheduleTaskRequest} implementation for clients.
 * <p>
 * Serializes {{@link #getRawData()} as json.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientApiScheduleTaskRequestImpl extends AbstractApiScheduleTaskRequestImpl {

	private String rawData;
	private transient JsonNode mappedData = null;

	public ClientApiScheduleTaskRequestImpl() {
		super();
	}

	public ClientApiScheduleTaskRequestImpl(String taskEntryName) {
		super(taskEntryName);
	}

	@JsonProperty("data")
	@JsonIgnore(false)
	@JsonRawValue
	public String getRawData() {
		return this.rawData;
	}

	public void setRawData(String data) {
		if (data == null) {
			throw new IllegalArgumentException("rawData cannot be null.");
		}

		this.rawData = data;
	}

	@JsonIgnore
	@Override
	public JsonNode getData() {
		if (this.rawData != null) {
			if (this.mappedData == null) {
				this.mappedData = ObjectMapperUtilityBuilderImpl.MAPPER.valueToTree(this.rawData);
			}

			return this.mappedData;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "ApiScheduleTaskRequestImpl [task=" + this.getTask() + ", encodedHeaders=" + this.getEncodedHeaders()
		        + ", encodedParameters=" + this.getEncodedParameters() + ", rawData=" + this.rawData + "]";
	}

}
