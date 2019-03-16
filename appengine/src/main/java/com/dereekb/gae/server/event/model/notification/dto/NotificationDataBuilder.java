package com.dereekb.gae.server.event.model.notification.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataBuilder;
import com.dereekb.gae.server.event.model.notification.Notification;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link Notification} to
 * {@link NotificationData}.
 *
 * @author dereekb
 */
public final class NotificationDataBuilder extends OwnedDatabaseModelDataBuilder<Notification, NotificationData> {

	public NotificationDataBuilder() {
		super(NotificationData.class);
	}

	public NotificationDataBuilder(Factory<NotificationData> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public NotificationData convertSingle(Notification input) throws ConversionFailureException {
		NotificationData data = super.convertSingle(input);
		
		data.setDateValue(input.getDate());
		data.setType(input.getType());
		data.setRead(input.getRead());
		data.setCode(input.getCode());

		return data;
	}

}
