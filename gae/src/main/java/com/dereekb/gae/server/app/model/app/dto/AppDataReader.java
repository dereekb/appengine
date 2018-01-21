package com.dereekb.gae.server.app.model.app.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.dto.DatedDatabaseModelDataReader;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link App} to
 * {@link AppData}.
 *
 * @author dereekb
 */
public final class AppDataReader extends DatedDatabaseModelDataReader<App, AppData> {

	private static final ObjectifyKeyUtility<Login> LOGIN_KEY_UTIL = ObjectifyKeyUtility.make(Login.class);

	public AppDataReader() {
		super(App.class);
	}

	public AppDataReader(Factory<App> factory) {
		super(factory);
	}

	@Override
	public App convertSingle(AppData input) throws ConversionFailureException {
		App model = super.convertSingle(input);

		// Info
		model.setName(input.getName());
		model.setSecret(input.getSecret());
		model.setLevelCode(input.getLevel());

		// Links
		model.setLogin(LOGIN_KEY_UTIL.keyFromId(input.getLogin()));

		return model;
	}

}
