package com.dereekb.gae.server.notification.model.token.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelDataReader;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link NotificationSettings} to
 * {@link NotificationSettingsData}.
 *
 * @author dereekb
 */
public final class NotificationSettingsDataReader extends DatabaseModelDataReader<NotificationSettings, NotificationSettingsData> {

	public NotificationSettingsDataReader() {
		super(NotificationSettings.class);
	}

	public NotificationSettingsDataReader(Factory<NotificationSettings> factory) {
		super(factory);
	}

	@Override
	public NotificationSettings convertSingle(NotificationSettingsData input) throws ConversionFailureException {
		NotificationSettings model = super.convertSingle(input);

		// Data
		// TODO: ...

		return model;
	}

}
