package com.dereekb.gae.server.event.model.shared.webhook.impl;

import java.util.List;

import com.dereekb.gae.server.event.model.shared.event.ModelEventData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Web Hook Data representation of {@link ModelEventData}. The data is the DTO
 * forms of the models.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelWebHookEventDataImpl<D> extends ModelTypedWebHookEventDataImpl {

	public static final String MODELS_KEY = "models";

	private List<D> models;

	public ModelWebHookEventDataImpl() {
		super();
	}

	public ModelWebHookEventDataImpl(String modelType, List<D> models) {
		this(ModelEventData.EVENT_DATA_TYPE, modelType, models);
	}

	public ModelWebHookEventDataImpl(String type, String modelType, List<D> models) {
		super(type, modelType);
		this.setModels(models);
	}

	public List<D> getModels() {
		return this.models;
	}

	public void setModels(List<D> models) {
		this.models = models;
	}

	@Override
	public String toString() {
		return "ModelWebHookEventDataImpl [models=" + this.models + ", getModelType()=" + this.getModelType()
		        + ", getEventDataType()=" + this.getEventDataType() + "]";
	}

}
