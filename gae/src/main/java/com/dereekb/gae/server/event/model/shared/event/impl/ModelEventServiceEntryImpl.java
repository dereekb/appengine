package com.dereekb.gae.server.event.model.shared.event.impl;

import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.service.EventService;
import com.dereekb.gae.server.event.model.shared.event.service.ModelEventSubmitTaskFactory;
import com.dereekb.gae.server.event.model.shared.event.service.task.ModelEventSubscriptionTask;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <D>
 *            data type
 */
public class ModelEventServiceEntryImpl<T extends UniqueModel, D extends UniqueModel> extends TypedModelImpl
        implements ModelEventSubmitTaskFactory<T> {

	private EventService eventService;
	private BidirectionalConverter<T, D> converter;

	public ModelEventServiceEntryImpl(String modelType,
	        EventService eventService,
	        BidirectionalConverter<T, D> converter) {
		super(modelType);
		this.eventService = eventService;
		this.converter = converter;
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

	public BidirectionalConverter<T, D> getConverter() {
		return this.converter;
	}

	public void setConverter(BidirectionalConverter<T, D> converter) {
		if (converter == null) {
			throw new IllegalArgumentException("converter cannot be null.");
		}

		this.converter = converter;
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
			// TODO Auto-generated method stub

		}

	}

	// MARK: ModelEventData
	protected class ModelEventDataImpl extends AbstractModelEventDataImpl<T> {

		public ModelEventDataImpl(List<T> eventModels) {
			super(ModelEventServiceEntryImpl.this.getModelType(), eventModels);
		}

		// MARK:
		@Override
		public ApiResponseData getWebSafeData(Parameters parameters) {
			parameters = ParameterUtility.safe(parameters);



			// TODO Auto-generated method stub
			return null;
		}

	}

}
