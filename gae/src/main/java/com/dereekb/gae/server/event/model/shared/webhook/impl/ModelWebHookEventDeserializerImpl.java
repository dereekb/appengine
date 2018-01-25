package com.dereekb.gae.server.event.model.shared.webhook.impl;

import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventDeserializer;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventSerializer;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.WebHookEventData;

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
public class ModelWebHookEventDeserializerImpl<T extends UniqueModel, D extends UniqueModel> extends AbstractDirectionalConverter<WebHookEvent, Event>
        implements ModelWebHookEventDeserializer<T>, TypedModel {

	private TypedBidirectionalConverter<T, D> dtoConverter;

	public ModelWebHookEventDeserializerImpl(TypedBidirectionalConverter<T, D> dtoConverter) {
		this.setDtoConverter(dtoConverter);
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
	public Event convertSingle(WebHookEvent input) throws ConversionFailureException {
		EventType eventType = input.getEventType();

		WebHookEventData webHookEventData = input.getEventData();

		if (webHookEventData == null) {

		} else {

			// TODO: Convert to models

		}

		return null;
	}

	// MARK: TypedModel
	@Override
	public String getModelType() {
		return this.dtoConverter.getModelType();
	}

	@Override
	public String toString() {
		return "ModelWebHookEventSerializerImpl [dtoConverter=" + this.dtoConverter + "]";
	}

}
