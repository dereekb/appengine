package com.dereekb.gae.server.auth.model.pointer.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataReader;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link LoginPointerData} to
 * {@link LoginPointer}.
 *
 * @author dereekb
 */
public final class LoginPointerDataReader extends OwnedDatabaseModelDataReader<LoginPointer, LoginPointerData> {

	private static final ObjectifyKeyUtility<Login> LOGIN_KEY_UTIL = ObjectifyKeyUtility.make(Login.class);

	public LoginPointerDataReader() {
		super(LoginPointer.class);
	}

	@Override
	public LoginPointer convertSingle(LoginPointerData input) throws ConversionFailureException {
		LoginPointer model = super.convertSingle(input);

		// Data
		model.setEmail(input.getEmail());
		model.setTypeId(input.getType());

		// Links
		model.setLogin(LOGIN_KEY_UTIL.keyFromId(input.getLogin()));

		return model;
	}

}
