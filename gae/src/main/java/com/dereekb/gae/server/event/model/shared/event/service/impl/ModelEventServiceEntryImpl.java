package com.dereekb.gae.server.event.model.shared.event.service.impl;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.exception.IllegalModelTypeException;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.service.EventService;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.model.shared.event.impl.ModelEventDataImpl;
import com.dereekb.gae.server.event.model.shared.event.impl.ModelEventImpl;
import com.dereekb.gae.server.event.model.shared.event.service.ModelEventKeyLoader;
import com.dereekb.gae.server.event.model.shared.event.service.ModelEventSubmitTaskFactory;
import com.dereekb.gae.server.event.model.shared.event.service.task.ModelEventSubscriptionTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <D>
 *            data type
 */
public class ModelEventServiceEntryImpl<T extends UniqueModel, D extends UniqueModel>
        implements ModelEventSubmitTaskFactory<T>, ModelEventKeyLoader<T>, TypedModel {

	private EventService eventService;
	private ModelKeyListAccessorFactory<T> accessorFactory;
	private BidirectionalConverter<T, D> converter;

	private transient ModelEventKeyLoader<T> loader;

	public ModelEventServiceEntryImpl(EventService eventService,
	        ModelKeyListAccessorFactory<T> accessorFactory,
	        BidirectionalConverter<T, D> converter) {
		this.setEventService(eventService);
		this.setAccessorFactory(accessorFactory);
		this.setConverter(converter);
	}

	public EventService getEventService() {
		return this.eventService;
	}

	public void setEventService(EventService eventService) {
		if (eventService == null) {
			throw new IllegalArgumentException("eventService cannot be null.");
		}

		this.eventService = eventService;
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

	public BidirectionalConverter<T, D> getConverter() {
		return this.converter;
	}

	public void setConverter(BidirectionalConverter<T, D> converter) {
		if (converter == null) {
			throw new IllegalArgumentException("converter cannot be null.");
		}

		this.converter = converter;
	}

	// MARK: TypedModel
	@Override
	public String getModelType() {
		return this.accessorFactory.getModelType();
	}

	// MARK: ModelEventSubmitTaskFactory
	@Override
	public ModelEventSubscriptionTask<T> makeSubmitEventTask(EventType event) {
		return new ModelEventSubscriptionTaskImpl(event);
	}

	protected class ModelEventSubscriptionTaskImpl
	        implements ModelEventSubscriptionTask<T> {

		private EventType event;

		public ModelEventSubscriptionTaskImpl(EventType event) {
			this.setEvent(event);
		}

		public EventType getEvent() {
			return this.event;
		}

		public void setEvent(EventType event) {
			this.event = event;
		}

		// MARK: ModelEventSubscriptionTask
		@Override
		public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
			ModelEventDataImpl<T> data = new ModelEventDataImpl<T>(input);
			ModelEvent<T> event = new ModelEventImpl<T>(this.event, data);
			ModelEventServiceEntryImpl.this.eventService.submitEvent(event);
		}

	}

	// MARK: ModelEventKeyLoader
	@Override
	public ModelEvent<T> loadModelEvent(ModelKeyEvent keyEvent) throws IllegalModelTypeException {
		return this.getLoader().loadModelEvent(keyEvent);
	}

	public ModelEventKeyLoader<T> getLoader() {
		if (this.loader == null) {
			this.loader = new ModelEventLoaderImpl<T>(this.accessorFactory);
		}

		return this.loader;
	}

}
