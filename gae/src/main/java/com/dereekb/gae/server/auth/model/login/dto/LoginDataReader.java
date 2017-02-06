package com.dereekb.gae.server.auth.model.login.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.dto.DescribedModelDataReader;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link Login} to
 * {@link LoginData}.
 *
 * @author dereekb
 */
public final class LoginDataReader extends DescribedModelDataReader<Login, LoginData> {

	private static final ObjectifyKeyUtility<LoginPointer> LOGIN_POINTER_KEY_UTIL = ObjectifyKeyUtility
	        .make(LoginPointer.class);

	public LoginDataReader() {
		super(Login.class);
	}

	public LoginDataReader(Factory<Login> factory) {
		super(factory);
	}

	@Override
	public Login convertSingle(LoginData input) throws ConversionFailureException {
		Login model = super.convertSingle(input);

		// Data
		model.setDate(input.getDateValue());
		model.setRoles(input.getRoles());
		model.setGroup(input.getGroup());

		// Links
		model.setPointers(LOGIN_POINTER_KEY_UTIL.setFromStrings(input.getPointers()));

		return model;
	}

}
