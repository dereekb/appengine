package com.dereekb.gae.server.event.model.shared.webhook.impl;

import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventSerializer;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.impl.WebHookEventImpl;

/**
 * {@link ModelWebHookEventSerializer} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <D>
 *            model dto type
 */
public class ModelWebHookEventSerializerImpl<T extends UniqueModel, D extends UniqueModel> extends AbstractDirectionalConverter<Event, WebHookEvent>
        implements ModelWebHookEventSerializer<T>, TypedModel {

	public static boolean DEFAULT_KEYS_ONLY = true;

	private boolean keysOnly;
	private TypedBidirectionalConverter<T, D> dtoConverter;

	public ModelWebHookEventSerializerImpl(TypedBidirectionalConverter<T, D> dtoConverter) {
		this(dtoConverter, DEFAULT_KEYS_ONLY);
	}

	public ModelWebHookEventSerializerImpl(TypedBidirectionalConverter<T, D> dtoConverter, boolean keysOnly) {
		this.setDtoConverter(dtoConverter);
		this.setKeysOnly(keysOnly);
	}

	public boolean isKeysOnly() {
		return this.keysOnly;
	}

	public void setKeysOnly(boolean keysOnly) {
		this.keysOnly = keysOnly;
	}

	public TypedBidirectionalConverter<T, D> getDtoConverter() {
		return this.dtoConverter;
	}

	public void setDtoConverter(TypedBidirectionalConverter<T, D> dtoConverter) {
		if (dtoConverter == null) {
			throw new IllegalArgumentException("dtoConverter cannot be null.");
		}

		this.dtoConverter = dtoConverter;
	}

	// MARK: ModelWebHookEventSerializer
	@Override
	public WebHookEvent convertSingle(Event input) throws ConversionFailureException {
		return this.convertSingle(input, this.keysOnly);
	}

	@Override
	public WebHookEvent convertSingle(Event input,
	                                  boolean keysOnly)
	        throws ConversionFailureException {
		WebHookEventImpl event = new WebHookEventImpl(input.getEventType());
		EventData data = input.getEventData();

		if (data != null) {
			Object webHookData = this.convertEventData(data, keysOnly);
			event.setData(webHookData);
		}

		return event;
	}

	@SuppressWarnings("unchecked")
	private Object convertEventData(EventData data,
	                                boolean keysOnly)
	        throws ConversionFailureException {
		Class<?> dataClass = data.getClass();

		if (ModelKeyEventData.class.isAssignableFrom(dataClass)) {
			if (!keysOnly && ModelEventData.class.isAssignableFrom(dataClass)) {

				// Treat as event data.
				return this.convertModelEventData((ModelEventData<T>) data);
			} else {

				// Treat as key data.
				return this.convertModelKeyEventData((ModelKeyEventData) data);
			}
		} else {
			throw new ConversionFailureException(
			        "ModelWebHookSerializer cannot convert data type: " + dataClass.getSimpleName());
		}
	}

	private ModelWebHookEventDataImpl<D> convertModelEventData(ModelEventData<T> data) {
		List<T> models = data.getEventModels();
		List<D> dtoModels = this.dtoConverter.convertTo(models);
		return new ModelWebHookEventDataImpl<D>(this.getModelType(), dtoModels);
	}

	private ModelKeyWebHookEventDataImpl convertModelKeyEventData(ModelKeyEventData data)
	        throws ConversionFailureException {
		List<ModelKey> keys = data.getEventModelKeys();
		List<String> stringKeys = ModelKey.readStringKeys(keys);
		return new ModelKeyWebHookEventDataImpl(this.getModelType(), stringKeys);
	}

	// MARK: TypedModel
	@Override
	public String getModelType() {
		return this.dtoConverter.getModelType();
	}

	@Override
	public String toString() {
		return "ModelWebHookEventSerializerImpl [keysOnly=" + this.keysOnly + ", dtoConverter=" + this.dtoConverter
		        + "]";
	}

}
