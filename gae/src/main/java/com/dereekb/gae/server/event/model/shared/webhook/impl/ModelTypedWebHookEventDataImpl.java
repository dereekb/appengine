package com.dereekb.gae.server.event.model.shared.webhook.impl;

import javax.validation.constraints.NotNull;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.event.webhook.impl.WebHookEventDataImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link WebHookEventDataImpl} that implements {@link TypedModel}.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelTypedWebHookEventDataImpl extends WebHookEventDataImpl
        implements TypedModel {

	@NotNull
	private String modelType;

	public ModelTypedWebHookEventDataImpl() {}

	public ModelTypedWebHookEventDataImpl(String type, String modelType) {
		super(type);
		this.setModelType(modelType);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	public String toString() {
		return "ModelTypedWebHookEventDataImpl [modelType=" + this.modelType + ", getEventDataType()="
		        + this.getEventDataType() + "]";
	}

}
