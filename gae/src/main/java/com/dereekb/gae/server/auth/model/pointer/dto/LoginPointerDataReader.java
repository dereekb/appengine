package com.dereekb.gae.server.auth.model.pointer.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link LoginPointer} to
 * {@link LoginPointerData}.
 *
 * @author dereekb
 */
public final class LoginPointerDataReader extends AbstractDirectionalConverter<LoginPointerData, LoginPointer> {

	private static final StringModelKeyConverter KEY_CONVERTER = StringModelKeyConverterImpl.CONVERTER;

	private static final ObjectifyKeyUtility<Login> LOGIN_KEY_UTIL = ObjectifyKeyUtility.make(Login.class);

	@Override
	public LoginPointer convertSingle(LoginPointerData input) throws ConversionFailureException {
		LoginPointer model = new LoginPointer();

		// Identifier
		String stringIdentifier = input.getKey();
		model.setModelKey(KEY_CONVERTER.safeConvert(stringIdentifier));

		// Links
		model.setLogin(LOGIN_KEY_UTIL.keyFromId(input.getLogin()));

		return model;
	}

}
