package com.dereekb.gae.server.event.model.shared.webhook.impl;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventConverter;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventDeserializer;
import com.dereekb.gae.server.event.model.shared.webhook.ModelWebHookEventSerializer;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.impl.WebHookEventConverterImpl;

/**
 * {@link ModelWebHookEventConverter} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelWebHookEventConverterImpl<T extends UniqueModel> extends WebHookEventConverterImpl
        implements ModelWebHookEventConverter<T> {

	public ModelWebHookEventConverterImpl(ModelWebHookEventSerializer<T> serializer,
	        ModelWebHookEventDeserializer<T> deserializer) {
		super(serializer, deserializer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelWebHookEventSerializer<T> getSerializer() {
		return (ModelWebHookEventSerializer<T>) super.getSerializer();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelWebHookEventDeserializer<T> getDeserializer() {
		return (ModelWebHookEventDeserializer<T>) super.getDeserializer();
	}

	// MARK: ModelWebHookEventConverter
	@Override
	public WebHookEvent convertSingle(Event input,
	                                  boolean keysOnly)
	        throws ConversionFailureException {
		return this.getSerializer().convertSingle(input, keysOnly);
	}

	@Override
	public String toString() {
		return "ModelWebHookEventConverterImpl [getSerializer()=" + this.getSerializer() + ", getDeserializer()="
		        + this.getDeserializer() + "]";
	}

}
