package com.dereekb.gae.server.auth.model.key.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataReader;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;
import com.googlecode.objectify.Key;

/**
 * {@link DirectionalConverter} for converting a {@link LoginKeyData} to
 * {@link LoginKey}.
 *
 * @author dereekb
 */
public final class LoginKeyDataReader extends OwnedDatabaseModelDataReader<LoginKey, LoginKeyData> {

	private static final ObjectifyKeyUtility<Login> LOGIN_KEY_UTIL = ObjectifyKeyUtility
	        .make(Login.class);
	private static final ObjectifyKeyUtility<LoginPointer> LOGIN_POINTER_KEY_UTIL = ObjectifyKeyUtility
	        .make(LoginPointer.class);

	public LoginKeyDataReader() {
		super(LoginKey.class);
	}

	public LoginKeyDataReader(Factory<LoginKey> factory) {
		super(factory);
	}

	@Override
	public LoginKey convertSingle(LoginKeyData input) throws ConversionFailureException {
		LoginKey model = super.convertSingle(input);

		// Data
		model.setDate(input.getDateValue());
		model.setName(input.getName());
		model.setMask(input.getMask());
		model.setVerification(input.getVerification());
		model.setExpiration(input.getExpiration());

		// Links
		Key<Login> login = LOGIN_KEY_UTIL.keyFromId(input.getLogin());

		if (login != null) {
			model.setLogin(login);
		}

		Key<LoginPointer> loginPointer = LOGIN_POINTER_KEY_UTIL.keyFromString(input.getPointer());

		if (loginPointer != null) {
			model.setLoginPointer(loginPointer);
		}

		return model;
	}

}
