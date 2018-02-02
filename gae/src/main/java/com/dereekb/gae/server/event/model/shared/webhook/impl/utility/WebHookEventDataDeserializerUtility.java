package com.dereekb.gae.server.event.model.shared.webhook.impl.utility;

import java.util.List;

import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelKeyWebHookEventDataImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelTypedWebHookEventDataImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelWebHookEventDataImpl;
import com.dereekb.gae.server.event.webhook.WebHookEventData;
import com.dereekb.gae.utilities.data.ObjectMapperUtility;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Utility for deserializing a {@link WebHookEventData} as a
 * {@link ModelTypedWebHookEventDataImpl} or related type.
 *
 * @author dereekb
 *
 */
public class WebHookEventDataDeserializerUtility {

	private final WebHookEventData webHookEventData;

	private transient ModelWebHookEventDataType dataType;

	public WebHookEventDataDeserializerUtility(WebHookEventData webHookEventData) {
		if (webHookEventData == null) {
			throw new IllegalArgumentException();
		}

		this.webHookEventData = webHookEventData;
	}

	public WebHookEventData getWebHookEventData() {
		return this.webHookEventData;
	}

	public JsonNode getJsonNode() {
		return this.webHookEventData.getJsonNode();
	}

	// MARK: Utility
	public String getModelType() {
		return this.getJsonNode().get(ModelTypedWebHookEventDataImpl.MODEL_TYPE_FIELD).asText();
	}

	public boolean isKeysEvent() {
		return this.getType() == ModelWebHookEventDataType.KEYS;
	}

	public JsonNode getKeysNode() {
		if (!this.isKeysEvent()) {
			throw new UnsupportedOperationException("Is not a keys event.");
		}

		return this.getJsonNode().get(ModelKeyWebHookEventDataImpl.KEYS_KEY);
	}

	public List<String> getStringKeys() {
		JsonNode keysNode = this.getKeysNode();
		ObjectMapperUtility mapperUtility = ObjectMapperUtilityBuilderImpl.SINGLETON.nullSafe().make();
		return mapperUtility.safeMapArrayToList(keysNode, String.class);
	}

	public boolean isModelsEvent() {
		return this.getType() == ModelWebHookEventDataType.MODELS;
	}

	public JsonNode getModelsNode() {
		if (!this.isModelsEvent()) {
			throw new UnsupportedOperationException("Is not a models event.");
		}

		return this.getJsonNode().get(ModelWebHookEventDataImpl.MODELS_KEY);
	}

	public <T> List<T> serializeModels(Class<T> type) {
		JsonNode modelsNode = this.getModelsNode();
		ObjectMapperUtility utility = ObjectMapperUtilityBuilderImpl.SINGLETON.nullSafe().make();
		return utility.safeMapArrayToList(modelsNode, type);
	}

	public ModelWebHookEventDataType getType() {
		if (this.dataType == null) {
			JsonNode rootData = this.webHookEventData.getJsonNode();

			if (rootData.has(ModelWebHookEventDataImpl.MODELS_KEY)) {
				this.dataType = ModelWebHookEventDataType.MODELS;
			} else if (rootData.has(ModelKeyWebHookEventDataImpl.KEYS_KEY)) {
				this.dataType = ModelWebHookEventDataType.KEYS;
			} else {
				this.dataType = ModelWebHookEventDataType.INVALID;
			}
		}

		return this.dataType;
	}

	public enum ModelWebHookEventDataType {

		KEYS,

		MODELS,

		INVALID

	}

	@Override
	public String toString() {
		return "WebHookEventDataDeserializerUtility [webHookEventData=" + this.webHookEventData + "]";
	}

}
