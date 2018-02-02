package com.dereekb.gae.server.event.webhook.service.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventSerializer;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveEntryContainer;
import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;

/**
 * Primary {@link WebHookEventSerializer} that delegates serialization to other
 * entries based on the group of the input {@link Event}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractWebHookEventSerializerImpl extends AbstractDirectionalConverter<Event, WebHookEvent>
        implements WebHookEventSerializer {

	private CaseInsensitiveEntryContainer<WebHookEventSerializer> entries;

	public AbstractWebHookEventSerializerImpl(Map<String, ? extends WebHookEventSerializer> entries) {
		super();
		this.setEntries(entries);
	}

	public CaseInsensitiveEntryContainer<WebHookEventSerializer> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, ? extends WebHookEventSerializer> entries) {
		this.setEntries(new CaseInsensitiveEntryContainerImpl<WebHookEventSerializer>(entries));
	}

	public void setEntries(CaseInsensitiveEntryContainer<WebHookEventSerializer> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("entries cannot be null.");
		}

		this.entries = entries;
	}

	// MARK: WebHookEventSerializer
	@Override
	public WebHookEvent convertSingle(Event input) throws ConversionFailureException {
		String type = this.getEntryKeyForEvent(input);

		try {
			return this.entries.getEntryForType(type).convertSingle(input);
		} catch (RuntimeException e) {
			throw new ConversionFailureException(e);
		}
	}

	protected abstract String getEntryKeyForEvent(Event input);

}
