package com.dereekb.gae.server.event.webhook.service.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventDeserializer;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveEntryContainer;
import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;

/**
 * Primary {@link WebHookEventDeserializer} entry point
 *
 * @author dereekb
 *
 */
public abstract class AbstractWebHookEventDeserializerImpl extends AbstractDirectionalConverter<WebHookEvent, Event>
        implements WebHookEventDeserializer {

	private CaseInsensitiveEntryContainer<WebHookEventDeserializer> entries;

	public AbstractWebHookEventDeserializerImpl(Map<String, ? extends WebHookEventDeserializer> entries) {
		this.setEntries(entries);
	}

	public CaseInsensitiveEntryContainer<WebHookEventDeserializer> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, ? extends WebHookEventDeserializer> entries) {
		this.setEntries(new CaseInsensitiveEntryContainerImpl<WebHookEventDeserializer>(entries));
	}

	public void setEntries(CaseInsensitiveEntryContainer<WebHookEventDeserializer> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("entries cannot be null.");
		}

		this.entries = entries;
	}

	// MARK: WebHookEventDeserializer
	@Override
	public Event convertSingle(WebHookEvent input) throws ConversionFailureException {
		String type = this.getEntryKeyForWebHookEvent(input);

		try {
			return this.entries.getEntryForType(type).convertSingle(input);
		} catch (RuntimeException e) {
			throw new ConversionFailureException(e);
		}
	}

	protected abstract String getEntryKeyForWebHookEvent(WebHookEvent input);

}
