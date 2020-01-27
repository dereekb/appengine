package com.dereekb.gae.server.auth.model.login.dto;

import java.util.Date;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.links.descriptor.impl.dto.DescribedDatabaseModelDataReader;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link Login} to
 * {@link LoginData}.
 *
 * @author dereekb
 */
public final class LoginDataReader extends DescribedDatabaseModelDataReader<Login, LoginData> {

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
		model.setRoot(input.getRoot());
		model.setRoles(input.getRoles());
		model.setGroup(input.getGroup());
		model.setDisabled(input.getDisabled());

		Date authReset = input.getAuthResetValue();
		if (authReset != null) {
			model.setAuthReset(authReset);
		}

		return model;
	}

}
