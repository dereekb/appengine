package com.thevisitcompany.gae.deprecated.web.response.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiModelResponse<T, K> extends ApiIdentifierResponse<K> {

	public static final String MODELS_KEY = "models";

	public ApiModelResponse() {}

	public ApiModelResponse(T model) {
		this.setModel(model);
	}

	public ApiModelResponse(List<T> models) {
		this.setModels(models);
	}

	public void setModel(T model) {
		List<T> modelArray = new ArrayList<T>();
		modelArray.add(model);
		this.setModels(modelArray);
	}

	public void setModels(List<T> models) {
		this.putInfo(MODELS_KEY, models);
	}

	@JsonIgnore
	@SuppressWarnings("unchecked")
	public List<T> getModels() {
		Object modelsObject = this.readInfo(MODELS_KEY);
		List<T> models = null;

		if (modelsObject != null) {
			models = (List<T>) modelsObject;
		}

		return models;
	}

	@JsonIgnore
	public List<T> getOrCreateModels() {
		List<T> models = this.getModels();

		if (models == null) {
			models = new ArrayList<T>();
			this.setModels(models);
		}

		return models;
	}

	public boolean hasModelInfo() {
		return this.hasInfo(MODELS_KEY);
	}

}
