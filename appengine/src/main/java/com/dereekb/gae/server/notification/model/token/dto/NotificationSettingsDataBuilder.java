package com.dereekb.gae.server.notification.model.token.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelDataBuilder;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link NotificationSettings} to
 * {@link NotificationSettingsData}.
 *
 * @author dereekb
 */
public final class NotificationSettingsDataBuilder extends DatabaseModelDataBuilder<NotificationSettings, NotificationSettingsData> {

	public NotificationSettingsDataBuilder() {
		super(NotificationSettingsData.class);
	}

	public NotificationSettingsDataBuilder(Factory<NotificationSettingsData> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public NotificationSettingsData convertSingle(NotificationSettings input) throws ConversionFailureException {
		NotificationSettingsData data = super.convertSingle(input);

		// Data
		// TODO: ...

		return data;
	}

}
