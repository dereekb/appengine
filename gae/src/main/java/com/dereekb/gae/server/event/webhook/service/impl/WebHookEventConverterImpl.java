package com.dereekb.gae.server.event.webhook.service.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventConverter;
import com.dereekb.gae.server.event.webhook.service.WebHookEventDeserializer;
import com.dereekb.gae.server.event.webhook.service.WebHookEventSerializer;

/**
 * {@link WebHookEventConverter} implementation.
 *
 * @author dereekb
 *
 */
public class WebHookEventConverterImpl
        implements WebHookEventConverter {

	private WebHookEventSerializer serializer;
	private WebHookEventDeserializer deserializer;

	public WebHookEventConverterImpl(WebHookEventSerializer serializer, WebHookEventDeserializer deserializer) {
		super();
		this.setSerializer(serializer);
		this.setDeserializer(deserializer);
	}

	public WebHookEventSerializer getSerializer() {
		return this.serializer;
	}

	public void setSerializer(WebHookEventSerializer serializer) {
		if (serializer == null) {
			throw new IllegalArgumentException("serializer cannot be null.");
		}

		this.serializer = serializer;
	}

	public WebHookEventDeserializer getDeserializer() {
		return this.deserializer;
	}

	public void setDeserializer(WebHookEventDeserializer deserializer) {
		if (deserializer == null) {
			throw new IllegalArgumentException("deserializer cannot be null.");
		}

		this.deserializer = deserializer;
	}

	// MARK: WebHookEventConverter
	@Override
	public WebHookEvent convertSingle(Event input) throws ConversionFailureException {
		return this.serializer.convertSingle(input);
	}

	@Override
	public List<WebHookEvent> convert(Collection<? extends Event> input) throws ConversionFailureException {
		return this.serializer.convert(input);
	}

	@Override
	public List<WebHookEvent> convertTo(Collection<? extends Event> input) throws ConversionFailureException {
		return this.serializer.convert(input);
	}

	@Override
	public Event convertSingle(WebHookEvent input) throws ConversionFailureException {
		return this.deserializer.convertSingle(input);
	}

	@Override
	public List<Event> convertFrom(Collection<? extends WebHookEvent> input) throws ConversionFailureException {
		return this.deserializer.convert(input);
	}

	@Override
	public String toString() {
		return "WebHookEventConverterImpl [serializer=" + this.serializer + ", deserializer=" + this.deserializer + "]";
	}

}
