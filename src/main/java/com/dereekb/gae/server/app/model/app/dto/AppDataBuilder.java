package com.dereekb.gae.server.app.model.app.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.datastore.models.dto.DatedDatabaseModelDataBuilder;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link App} to
 * {@link AppData}.
 *
 * @author dereekb
 */
public final class AppDataBuilder extends DatedDatabaseModelDataBuilder<App, AppData> {

	public AppDataBuilder() {
		super(AppData.class);
	}

	public AppDataBuilder(Factory<AppData> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public AppData convertSingle(App input) throws ConversionFailureException {
		AppData data = super.convertSingle(input);

		// Info
		data.setName(input.getName());
		data.setSecret(input.getSecret());
		data.setLevel(input.getLevelCode());

		// Links
		data.setLogin(ObjectifyKeyUtility.readKeyIdentifier(input.getLogin()));

		return data;
	}

}
