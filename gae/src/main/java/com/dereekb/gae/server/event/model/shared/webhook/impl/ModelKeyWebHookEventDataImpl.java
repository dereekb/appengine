package com.dereekb.gae.server.event.model.shared.webhook.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Web Hook Data representation of {@link ModelKeyEventData}.
 *
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelKeyWebHookEventDataImpl extends ModelTypedWebHookEventDataImpl {

	private List<String> keys;

	public ModelKeyWebHookEventDataImpl() {
		super();
	}

	public ModelKeyWebHookEventDataImpl(String modelType, List<String> keys) {
		this(ModelKeyEventData.EVENT_DATA_TYPE, modelType, keys);
	}

	public ModelKeyWebHookEventDataImpl(String type, String modelType, List<String> keys) {
		super(type, modelType);
		this.setKeys(keys);
	}

	public List<String> getKeys() {
		return this.keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	@JsonIgnore
	public void setModelKeys(List<ModelKey> keys) {
		this.setKeys(ModelKey.keysAsStrings(keys));
	}

	@Override
	public String toString() {
		return "ModelKeyWebHookEventDataImpl [keys=" + this.keys + ", getModelType()=" + this.getModelType()
		        + ", getEventDataType()=" + this.getEventDataType() + "]";
	}

}
