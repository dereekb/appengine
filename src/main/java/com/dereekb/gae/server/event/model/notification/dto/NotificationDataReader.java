package com.dereekb.gae.server.event.model.notification.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataReader;
import com.dereekb.gae.server.event.model.notification.Notification;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link Notification} to
 * {@link NotificationData}.
 *
 * @author dereekb
 */
public final class NotificationDataReader extends OwnedDatabaseModelDataReader<Notification, NotificationData> {

	public NotificationDataReader() {
		super(Notification.class);
	}

	public NotificationDataReader(Factory<Notification> factory) {
		super(factory);
	}

	@Override
	public Notification convertSingle(NotificationData input) throws ConversionFailureException {
		Notification model = super.convertSingle(input);

		model.setRead(input.getRead());
		model.setType(input.getType());
		model.setCode(input.getCode());
		model.setDate(input.getDateValue());
		
		return model;
	}

}
