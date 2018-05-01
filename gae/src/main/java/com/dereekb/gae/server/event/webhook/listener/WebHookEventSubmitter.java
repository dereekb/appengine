package com.dereekb.gae.server.event.webhook.listener;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventConverter;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.impl.FilterImpl;

/**
 * {@link EventServiceListener} implementation that
 *
 * @author dereekb
 *
 */
public class WebHookEventSubmitter
        implements EventServiceListener {

	private WebHookEventConverter converter;
	private WebHookEventSubmitterDelegate delegate;

	private Filter<Event> eventFilter = FilterImpl.pass();

	public WebHookEventSubmitter(WebHookEventConverter converter, WebHookEventSubmitterDelegate delegate) {
		this(converter, delegate, FilterImpl.pass());
	}

	public WebHookEventSubmitter(WebHookEventConverter converter,
	        WebHookEventSubmitterDelegate delegate,
	        Filter<Event> eventFilter) {
		super();
		this.setConverter(converter);
		this.setDelegate(delegate);
		this.setEventFilter(eventFilter);
	}

	public WebHookEventConverter getConverter() {
		return this.converter;
	}

	public void setConverter(WebHookEventConverter converter) {
		if (converter == null) {
			throw new IllegalArgumentException("converter cannot be null.");
		}

		this.converter = converter;
	}

	public WebHookEventSubmitterDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(WebHookEventSubmitterDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public Filter<Event> getEventFilter() {
		return this.eventFilter;
	}

	public void setEventFilter(Filter<Event> eventFilter) {
		if (eventFilter == null) {
			throw new IllegalArgumentException("eventFilter cannot be null.");
		}

		this.eventFilter = eventFilter;
	}

	// MARK: EventServiceListener
	@Override
	public void handleEvent(Event event) {
		if (this.eventFilter.filterObject(event).isPass()) {
			WebHookEvent webHookEvent = this.converter.convertSingle(event);
			this.delegate.submitEvent(webHookEvent);
		}
	}

	@Override
	public String toString() {
		return "WebHookEventSubmitter [converter=" + this.converter + ", delegate=" + this.delegate + ", eventFilter="
		        + this.eventFilter + "]";
	}

}
