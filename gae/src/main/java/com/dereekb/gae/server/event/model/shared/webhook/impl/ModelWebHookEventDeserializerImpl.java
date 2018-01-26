package com.dereekb.gae.server.event.model.shared.webhook.impl;

import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.EventImpl;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;
import com.dereekb.gae.server.event.model.shared.event.impl.ModelEventDataImpl;
import com.dereekb.gae.server.event.model.shared.event.impl.ModelEventImpl;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventDeserializer;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.WebHookEventData;
import com.dereekb.gae.utilities.data.ObjectMapperUtility;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ModelWebHookEventDeserializer} implementation.
 * <p>
 * Events always have {@link ModelEventData} associated with them.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <D>
 *            model dto type
 */
public class ModelWebHookEventDeserializerImpl<T extends UniqueModel, D extends UniqueModel> extends AbstractDirectionalConverter<WebHookEvent, Event>
        implements ModelWebHookEventDeserializer<T>, TypedModel {

	private ModelKeyListAccessorFactory<T> accessorFactory;
	private TypedBidirectionalConverter<T, D> typedConverter;

	public ModelWebHookEventDeserializerImpl(ModelKeyListAccessorFactory<T> accessorFactory,
	        TypedBidirectionalConverter<T, D> typedConverter) {
		super();
		this.setAccessorFactory(accessorFactory);
		this.setTypedConverter(typedConverter);
	}

	public ModelKeyListAccessorFactory<T> getAccessorFactory() {
		return this.accessorFactory;
	}

	public void setAccessorFactory(ModelKeyListAccessorFactory<T> accessorFactory) {
		if (accessorFactory == null) {
			throw new IllegalArgumentException("accessorFactory cannot be null.");
		}

		this.accessorFactory = accessorFactory;
	}

	public TypedBidirectionalConverter<T, D> getTypedConverter() {
		return this.typedConverter;
	}

	public void setTypedConverter(TypedBidirectionalConverter<T, D> typedConverter) {
		if (typedConverter == null) {
			throw new IllegalArgumentException("typedConverter cannot be null.");
		}

		this.typedConverter = typedConverter;
	}

	// MARK: ModelWebHookEventSerializer
	@Override
	public Event convertSingle(WebHookEvent input) throws ConversionFailureException {
		EventType eventType = input.getEventType();

		WebHookEventData webHookEventData = input.getEventData();

		if (webHookEventData == null) {
			return new EventImpl(eventType);
		} else {
			ModelEventData<T> data = this.convertEventData(webHookEventData);
			return new ModelEventImpl<T>(eventType, data);
		}
	}

	protected ModelEventData<T> convertEventData(WebHookEventData webHookEventData) {
		JsonNode rootData = webHookEventData.getJsonNode();

		if (rootData.has(ModelWebHookEventDataImpl.MODELS_KEY)) {
			// Convert models.
			return this.convertModelsEventData(webHookEventData);
		} else if (rootData.has(ModelKeyWebHookEventDataImpl.KEYS_KEY)) {
			// Convert keys.
			return this.convertKeysEventData(webHookEventData);
		} else {
			throw new ConversionFailureException("Data was unfamiliar.");
		}
	}

	private ModelEventData<T> convertKeysEventData(WebHookEventData webHookEventData) {

		JsonNode rootData = webHookEventData.getJsonNode();
		JsonNode keysNode = rootData.get(ModelKeyWebHookEventDataImpl.KEYS_KEY);

		ObjectMapperUtility utility = ObjectMapperUtilityBuilderImpl.SINGLETON.nullSafe().make();

		List<String> stringKeys = utility.safeMapArrayToList(keysNode, String.class);
		ModelKeyListAccessor<T> accessor = this.accessorFactory.createAccessorWithStringKeys(stringKeys);

		return new ModelEventDataImpl<T>(accessor);
	}

	private ModelEventData<T> convertModelsEventData(WebHookEventData webHookEventData) {

		JsonNode rootData = webHookEventData.getJsonNode();
		JsonNode modelsNode = rootData.get(ModelWebHookEventDataImpl.MODELS_KEY);

		ObjectMapperUtility utility = ObjectMapperUtilityBuilderImpl.SINGLETON.nullSafe().make();

		List<D> modelData = utility.safeMapArrayToList(modelsNode, this.typedConverter.getModelDataClass());
		List<T> models = this.typedConverter.convertFrom(modelData);

		ModelKeyListAccessor<T> accessor = this.accessorFactory.createAccessorWithModels(models);
		return new ModelEventDataImpl<T>(accessor);
	}

	// MARK: TypedModel
	@Override
	public String getModelType() {
		return this.typedConverter.getModelType();
	}

	@Override
	public String toString() {
		return "ModelWebHookEventDeserializerImpl [accessorFactory=" + this.accessorFactory + ", typedConverter="
		        + this.typedConverter + "]";
	}

}
